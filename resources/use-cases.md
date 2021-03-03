###### Use Cases
1. Simple Select - Find Nodes with a Specific Attribute
   <pre>
   Example: Find the Herb with the Latin Name of 'Verbena hastata'.
   
   MATCH (h:Herb {latinName:"Verbena hastata"}) RETURN h;  
   </pre>
   
   <pre>
   Example: Find Persons with the Last Name of 'Jones'.
   
   MATCH (p:Person {lastName:"Jones"}) RETURN p; 
   </pre>
   
2. Simple Select - Find Nodes Having a Specifed Edge Type 
   <pre>
   Example: Find Persons that use at least one Herb...meaning, they have 
   at least one Edge_Person_uses_herb_Herb edge.
   
   MATCH (p:Person)-[:Edge_Person_uses_Herb]->(:Herb) RETURN p; 
   
   Note - This will give you repeating nodes for Persons that use more 
   than one herb. If you want to distinct your resultset to only give
   unique Persons, use the following:
   
   MATCH (p:Person)-[:Edge_Person_uses_Herb]->(:Herb) WITH DISTINCT p RETURN p
   </pre>

3. Simple Select - Find Nodes Having a Specified Edge Type and Where the Related Node Has a Specific Attribute
   <pre>
      Example: Find Persons that the Herb Nettle
      
      MATCH (p:Person)-[:Edge_Person_uses_Herb]->(:Herb {herbKey:"Nettle"}) WITH DISTINCT p RETURN p
   </pre>
   

4. Simple Select - Find a Node Having a Specified Edge Type
   <pre>
      Example: Find All of the Nodes Connected to a Specified Node Via a Specified Edge
      
      MATCH (h:Herb {commonName:"Boneset"})-[r:Edge_Herb_has_Action]->(b) return *
   </pre>