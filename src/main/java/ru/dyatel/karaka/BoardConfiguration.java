package ru.dyatel.karaka;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class BoardConfiguration {

	private File workingDir;

	@Autowired
	public BoardConfiguration(ServletContext context) {
		JsonParser parser = new JsonParser();
		try (Reader config = new InputStreamReader(
				new ServletContextResource(context, "config.json").getInputStream())) {
			JsonElement root = parser.parse(config);
			workingDir = new File(
					root.getAsJsonObject().get("working_dir").getAsString()
			);
		} catch (Exception e) {
			workingDir = new File(System.getProperty("user.home") + "/Karaka");
		}
	}

}
