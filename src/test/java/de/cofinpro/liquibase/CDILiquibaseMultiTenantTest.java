package de.cofinpro.liquibase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class CDILiquibaseMultiTenantTest {

    private H2LiquibaseConfig config;

    @Before
    public void setUp() throws Exception {
        config = new H2LiquibaseConfig();
    }

    @After
    public void tearDown() throws Exception {
        config.close();
    }

    @Test
    public void performUpdate() throws Exception {
        CDILiquibaseMultiTenant liquibase = new CDILiquibaseMultiTenant(config);
        liquibase.performUpdate();

        final String query = "SELECT * FROM tenant2.user";
        try (Connection conn = config.getDataSource().getConnection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(query);
        ) {
            assertThat(result.first(), is(true));
            assertThat(result.getString("name"), is("Bert"));
        }

    }

}