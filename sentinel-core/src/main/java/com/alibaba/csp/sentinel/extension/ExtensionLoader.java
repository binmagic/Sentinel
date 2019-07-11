package com.alibaba.csp.sentinel.extension;

import com.alibaba.csp.sentinel.log.Logger;
import com.alibaba.csp.sentinel.log.LoggerFactory;
import com.alibaba.csp.sentinel.util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * Created by admin on 2019/7/9.
 */
public class ExtensionLoader<T>
{

	private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

	private static final String SERVICES_DIRECTORY = "META-INF/services/";

	private static final String SENTINEL_DIRECTORY = "META-INF/sentinel/";

	private static final String SENTINEL_INTERNAL_DIRECTORY = SENTINEL_DIRECTORY + "internal/";

	private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

	private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

	private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

	// ==============================

	private final Class<?> type;

	private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();

	private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

	private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
	private final Holder<Object> cachedActivateInstance = new Holder<>();
	private volatile Class<?> cachedActiveClass = null;

	private String cachedDefaultName = null;

	private Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();

	private ExtensionLoader(Class<?> type)
	{
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public T getActiveExtension()
	{
		getExtensionClasses();

		Object instance = cachedActivateInstance.get();
		if(instance == null)
		{
			synchronized(cachedActivateInstance)
			{
				instance = cachedActivateInstance.get();
				if(instance == null)
				{
					instance = createActivateExtension();
					cachedActivateInstance.set(instance);
				}
			}

		}

		return (T)instance;
	}

	private Object createActivateExtension()
	{
		try
		{
			if(cachedActiveClass == null){
				return getDefaultExtension();
			}
			return cachedActiveClass.newInstance();
		}
		catch(Exception e)
		{
			logger.error("createActivateExtension fail", e);
		}
		return null;
	}

	public Set<String> getSupportedExtensions()
	{
		Map<String, Class<?>> clazzes = getExtensionClasses();
		return Collections.unmodifiableSet(new TreeSet<>(clazzes.keySet()));
	}

	public String getExtensionName(T extensionInstance)
	{
		return getExtensionName(extensionInstance.getClass());
	}

	public String getExtensionName(Class<?> extensionClass)
	{
		getExtensionClasses();
		return cachedNames.get(extensionClass);
	}

	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type)
	{
		if(type == null)
		{
			throw new IllegalArgumentException("Extension type == null");
		}
		if(!type.isInterface())
		{
			throw new IllegalArgumentException("Extension type (" + type + ") is not an interface!");
		}
		if(!withExtensionAnnotation(type))
		{
			throw new IllegalArgumentException("Extension type (" + type +
					") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
		}

		ExtensionLoader<T> loader = (ExtensionLoader<T>)EXTENSION_LOADERS.get(type);
		if(loader == null)
		{
			EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
			loader = (ExtensionLoader<T>)EXTENSION_LOADERS.get(type);
		}
		return loader;
	}


	public T getExtension(String name)
	{
		if(StringUtil.isEmpty(name))
		{
			throw new IllegalArgumentException("Extension name == null");
		}
		if("true".equals(name))
		{
			return getDefaultExtension();
		}

		Holder<Object> holder = getOrCreateHolder(name);
		Object instance = holder.get();
		if(instance == null)
		{
			synchronized(holder)
			{
				instance = holder.get();
				if(instance == null)
				{
					instance = createExtension(name);
					holder.set(instance);
				}
			}
		}
		return (T)instance;
	}

	private Holder<Object> getOrCreateHolder(String name)
	{
		Holder<Object> holder = cachedInstances.get(name);
		if(holder == null)
		{
			cachedInstances.putIfAbsent(name, new Holder<>());
			holder = cachedInstances.get(name);
		}
		return holder;
	}

	@SuppressWarnings("unchecked")
	private T createExtension(String name)
	{
		Class<?> clazz = getExtensionClasses().get(name);
		if(clazz == null)
		{
			throw findException(name);
		}
		try
		{
			T instance = (T)EXTENSION_INSTANCES.get(clazz);
			if(instance == null)
			{
				EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
				instance = (T)EXTENSION_INSTANCES.get(clazz);
			}
			return instance;
		}
		catch(Throwable t)
		{
			throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
					type + ") couldn't be instantiated: " + t.getMessage(), t);
		}
	}


	private IllegalStateException findException(String name)
	{
		for(Map.Entry<String, IllegalStateException> entry : exceptions.entrySet())
		{
			if(entry.getKey().toLowerCase().contains(name.toLowerCase()))
			{
				return entry.getValue();
			}
		}
		StringBuilder buf = new StringBuilder("No such extension " + type.getName() + " by name " + name);


		int i = 1;
		for(Map.Entry<String, IllegalStateException> entry : exceptions.entrySet())
		{
			if(i == 1)
			{
				buf.append(", possible causes: ");
			}

			buf.append("\r\n(");
			buf.append(i++);
			buf.append(") ");
			buf.append(entry.getKey());
			buf.append(":\r\n");
			buf.append(StringUtil.toString(entry.getValue()));
		}
		return new IllegalStateException(buf.toString());
	}

	public T getDefaultExtension()
	{
		getExtensionClasses();
		if(StringUtil.isBlank(cachedDefaultName) || "true".equals(cachedDefaultName))
		{
			return null;
		}
		return getExtension(cachedDefaultName);
	}


	private Map<String, Class<?>> getExtensionClasses()
	{
		Map<String, Class<?>> classes = cachedClasses.get();
		if(classes == null)
		{
			synchronized(cachedClasses)
			{
				classes = cachedClasses.get();
				if(classes == null)
				{
					classes = loadExtensionClasses();
					cachedClasses.set(classes);
				}
			}
		}
		return classes;
	}

	/**
	 * load all of extension classes
	 * @return
	 */
	private Map<String, Class<?>> loadExtensionClasses()
	{
		cacheDefaultExtensionName();

		Map<String, Class<?>> extensionClasses = new HashMap<>();
		loadDirectory(extensionClasses, SENTINEL_INTERNAL_DIRECTORY, type.getName());
		loadDirectory(extensionClasses, SENTINEL_DIRECTORY, type.getName());
		loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName());
		return extensionClasses;
	}

	/**
	 * cache default extension
	 */
	private void cacheDefaultExtensionName()
	{
		final SPI defaultAnnotation = type.getAnnotation(SPI.class);
		if(defaultAnnotation != null)
		{
			String value = defaultAnnotation.value();
			if((value = value.trim()).length() > 0)
			{
				String[] names = NAME_SEPARATOR.split(value);
				if(names.length > 1)
				{
					throw new IllegalStateException("More than 1 default extension name on extension " + type.getName()
							+ ": " + Arrays.toString(names));
				}
				if(names.length == 1)
				{
					cachedDefaultName = names[0];
				}
			}
		}
	}

	private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type)
	{
		String fileName = dir + type;
		try
		{
			Enumeration<URL> urls;
			ClassLoader classLoader = findClassLoader();
			if(classLoader != null)
			{
				urls = classLoader.getResources(fileName);
			}
			else
			{
				urls = ClassLoader.getSystemResources(fileName);
			}
			if(urls != null)
			{
				while(urls.hasMoreElements())
				{
					java.net.URL resourceURL = urls.nextElement();
					loadResource(extensionClasses, classLoader, resourceURL);
				}
			}
		}
		catch(Throwable t)
		{
			logger.error("Exception occurred when loading extension class (interface: " +
					type + ", description file: " + fileName + ").", t);
		}
	}

	/**
	 * load defined in resource
	 * @param extensionClasses
	 * @param classLoader
	 * @param resourceURL jar url
	 */
	private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL)
	{
		try
		{
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8)))
			{
				String line;
				while((line = reader.readLine()) != null)
				{
					final int ci = line.indexOf('#');
					if(ci >= 0)
					{
						line = line.substring(0, ci);
					}
					line = line.trim();
					if(line.length() > 0)
					{
						try
						{
							String name = null;
							int i = line.indexOf('=');
							if(i > 0)
							{
								name = line.substring(0, i).trim();
								line = line.substring(i + 1).trim();
							}
							if(line.length() > 0)
							{
								loadClass(extensionClasses, Class.forName(line, true, classLoader), name);
							}
						}
						catch(Throwable t)
						{
							IllegalStateException e = new IllegalStateException("Failed to load extension class (interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
							exceptions.put(line, e);
						}
					}
				}
			}
		}
		catch(Throwable t)
		{
			logger.error("Exception occurred when loading extension class (interface: " +
					type + ", class file: " + resourceURL + ") in " + resourceURL, t);
		}
	}

	/**
	 * load class into cache
	 * @param extensionClasses
	 * @param clazz
	 * @param name
	 * @throws NoSuchMethodException
	 */
	private void loadClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name) throws NoSuchMethodException
	{
		if(!type.isAssignableFrom(clazz))
		{
			throw new IllegalStateException("Error occurred when loading extension class (interface: " +
					type + ", class line: " + clazz.getName() + "), class "
					+ clazz.getName() + " is not subtype of interface.");
		}

		clazz.getConstructor();

		String[] names = NAME_SEPARATOR.split(name);
		if(ArrayUtil.isNotEmpty(names))
		{
			cacheActivateClass(clazz, names[0]);
			for(String n : names)
			{
				cacheName(clazz, n);
				saveInExtensionClass(extensionClasses, clazz, name);
			}
		}
	}


	/**
	 * put clazz in extensionClasses
	 */
	private void saveInExtensionClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name)
	{
		Class<?> c = extensionClasses.get(name);
		if(c == null)
		{
			extensionClasses.put(name, clazz);
		}
		else if(c != clazz)
		{
			throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + name + " on " + c.getName() + " and " + clazz.getName());
		}
	}

	/**
	 * cache name
	 */
	private void cacheName(Class<?> clazz, String name)
	{
		if(!cachedNames.containsKey(clazz))
		{
			cachedNames.put(clazz, name);
		}
	}

	/**
	 * cache Activate class which is annotated with <code>Activate</code>
	 * <p>
	 * for compatibility, also cache class with old alibaba Activate annotation
	 */
	private void cacheActivateClass(Class<?> clazz, String name)
	{
		Activate activate = clazz.getAnnotation(Activate.class);
		if(activate != null)
		{
			cachedActiveClass = clazz;
		}
	}

	private static ClassLoader findClassLoader()
	{
		return ClassHelper.getClassLoader(ExtensionLoader.class);
	}

	private static <T> boolean withExtensionAnnotation(Class<T> type)
	{
		return type.isAnnotationPresent(SPI.class);
	}

}
