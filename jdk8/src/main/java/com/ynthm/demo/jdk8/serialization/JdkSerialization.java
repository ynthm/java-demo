package com.ynthm.demo.jdk8.serialization;

import java.io.*;

public class JdkSerialization {

  public byte[] serialize(Object obj) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bos);
    out.writeObject(obj);
    out.flush();
    return bos.toByteArray();
  }

  public <T> T deserialize(byte[] bytes, Class<T> clazz)
      throws IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
    return (T) in.readObject();
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Person person = new Person();
    person.setName("飞天");
    JdkSerialization serialization = new JdkSerialization();
    byte[] bytes = serialization.serialize(person);
    Person readPerson = serialization.deserialize(bytes, Person.class);
    System.out.println(readPerson);
  }
}
