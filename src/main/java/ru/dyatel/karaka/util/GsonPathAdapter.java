package ru.dyatel.karaka.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GsonPathAdapter extends TypeAdapter<Path> {

	@Override
	public void write(JsonWriter out, Path value) throws IOException {
		if (value == null) out.nullValue();
		else out.value(value.toString());
	}

	@Override
	public Path read(JsonReader in) throws IOException {
		if (JsonToken.NULL.equals(in.peek())) {
			in.skipValue();
			return null;
		}

		return Paths.get(in.nextString());
	}

}
