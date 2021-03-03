package utilities;

import org.neo4j.driver.*;

public class Neo4jConnection implements AutoCloseable {

    private final Driver driver;

    public Neo4jConnection( Neo4jConnectionProperties neo4jConnectionProperties )
    {
        driver = GraphDatabase.driver(neo4jConnectionProperties.getUri().toString(),
                AuthTokens.basic(neo4jConnectionProperties.getUserName().toString(),
                        neo4jConnectionProperties.getPassword().toString()));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void executeStatementTwo(final String statement)
    {
        try
        {
            Session session = driver.session();
            String transaction = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run (statement.toString());
                    return result.single().get(0).asString();
                }
            } );
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
