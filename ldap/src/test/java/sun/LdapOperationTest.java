package sun;

import org.junit.Test;
import sun.entity.Person;

import static org.junit.Assert.*;

/**
 * @author sumu
 * @date 12/31/2021 6:01 PM
 */
public class LdapOperationTest {

    @Test
    public void login() {
        LdapOperation ldapOperation = new LdapOperation("ldap://127.0.0.1:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();

        Person xiaoming = ldapOperation.search("小白");
        System.out.println(xiaoming);
    }

    @Test
    public void add() {
        LdapOperation ldapOperation = new LdapOperation("ldap://127.0.0.1:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();
        Person person = new Person();
        person.setUsername("小白");
        person.setPassword("123456");
        person.setAge("18");
        person.setPhone("10086");
        person.setUserEmail("123@test.com");
        ldapOperation.addPerson(person);
    }

    @Test
    public void updatePerson() {
        LdapOperation ldapOperation = new LdapOperation("ldap://127.0.0.1:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();
        Person person = new Person();
        person.setUsername("小白");
        person.setPassword("123456");
        person.setAge("18");
        person.setPhone("10086");
        person.setUserEmail("124@test.com");
        ldapOperation.updatePerson(person);
        System.out.println(ldapOperation.search("小白"));
    }

    @Test
    public void delete() {
        LdapOperation ldapOperation = new LdapOperation("ldap://127.0.0.1:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();
        Person person = new Person();
        person.setUsername("小白1");
        person.setPassword("123456");
        person.setAge("18");
        person.setPhone("10086");
        person.setUserEmail("124@test.com");
        System.out.println(ldapOperation.addPerson(person));
        System.out.println(ldapOperation.search("小白1"));
        ldapOperation.deletePerson("小白1");
        System.out.println(ldapOperation.search("小白1"));
    }

    @Test
    public void mockedUserLogin() {
        String username = "小白";
        String password = "123456";
        LdapOperation ldapOperation = new LdapOperation("ldap://127.0.0.1:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();
        ldapOperation.userLogin(username,password);
    }
}