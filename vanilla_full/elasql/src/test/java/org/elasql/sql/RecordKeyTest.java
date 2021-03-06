package org.elasql.sql;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.VarcharConstant;

public class RecordKeyTest {

	@Test
	public void testSerializationSingle() throws IOException, ClassNotFoundException {
		RecordKey key = new RecordKey("test_table", "test_field", new VarcharConstant("test_val"));
		RecordKey result = null;
		byte[] bytes = null;
		
		// Serialize the object to a byte array
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
				out.writeObject(key);
				out.flush();
				bytes = bos.toByteArray();
			}
		}
		
		// Deserialize the byte array
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream in = new ObjectInputStream(bis)) {
				result = (RecordKey) in.readObject();
			}
		}
		
		assertEquals("fails to deserialize the object of RecordKey", key, result);
	}
	
	@Test
	public void testSerializationMultiple() throws IOException, ClassNotFoundException {
		RecordKeyBuilder builder = new RecordKeyBuilder("test_table");
		builder.addFldVal("test_field_int", new IntegerConstant(1));
		builder.addFldVal("test_field_str", new VarcharConstant("test_val"));
		RecordKey key = builder.build();
		RecordKey result = null;
		byte[] bytes = null;
		
		// Serialize the object to a byte array
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
				out.writeObject(key);
				out.flush();
				bytes = bos.toByteArray();
			}
		}
		
		// Deserialize the byte array
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream in = new ObjectInputStream(bis)) {
				result = (RecordKey) in.readObject();
			}
		}
		
		assertEquals("fails to deserialize the object of RecordKey", key, result);
	}
}
