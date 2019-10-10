package com.tmean.email.models;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


public class ContactInputTest {

    private ContactInput contactInput;

    @Before
    public void setup(){
        contactInput = new ContactInput("name","email","subject","content");
    }

    @Test
    public void getName() {
        assertThat(contactInput.getName()).isEqualTo("name");
    }

    @Test
    public void setName() throws NoSuchFieldException, IllegalAccessException {
        String newName = "testName";
        contactInput.setName(newName);
        final Field field = ContactInput.class.getDeclaredField("name");
        field.setAccessible(true);
        assertThat(field.get((contactInput))).isEqualTo(newName);
    }

    @Test
    public void getEmail() {
        assertThat(contactInput.getEmail()).isEqualTo("email");
    }

    @Test
    public void setEmail() throws NoSuchFieldException, IllegalAccessException {
        String newEmail = "testEmail";
        contactInput.setEmail(newEmail);
        final Field field = ContactInput.class.getDeclaredField("email");
        field.setAccessible(true);
        assertThat(field.get((contactInput))).isEqualTo(newEmail);
    }

    @Test
    public void getSubject() {
        assertThat(contactInput.getSubject()).isEqualTo("subject");
    }

    @Test
    public void setSubject() throws NoSuchFieldException, IllegalAccessException {
        String newSubject = "testSubject";
        contactInput.setSubject(newSubject);
        final Field field = ContactInput.class.getDeclaredField("subject");
        field.setAccessible(true);
        assertThat(field.get((contactInput))).isEqualTo(newSubject);
    }

    @Test
    public void getContent() {
        assertThat(contactInput.getContent()).isEqualTo("content");
    }

    @Test
    public void setContent() throws NoSuchFieldException, IllegalAccessException {
        String newContent = "testContent";
        contactInput.setContent(newContent);
        final Field field = ContactInput.class.getDeclaredField("content");
        field.setAccessible(true);
        assertThat(field.get((contactInput))).isEqualTo(newContent);
    }

    @Test
    public void testToString() {
        assertThat(contactInput.toString()).isEqualTo("ContactInput{name='name', email='email', subject='subject', content='content'}");
    }
}