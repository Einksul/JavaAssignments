package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse test = new Warehouse();
        int queries = StdIn.readInt();
        System.out.println(queries);
        for(int i = 0; i < queries; i++)
        {
            String word = StdIn.readString();
            System.out.println(word);
            if(word.compareTo("add")==0)
            {
                System.out.print("hi");
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                test.addProduct(id, name, stock, day, demand);
            }
            else if(word.compareTo("delete")==0)
            {
                int id = StdIn.readInt();
                test.deleteProduct(id);
            }
            else if(word.compareTo("purchase")==0)
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                test.purchaseProduct(id, day, amount);
            }
            else if(word.compareTo("restock")==0)
            {
                int id = StdIn.readInt();
                System.out.println(id);
                int amount = StdIn.readInt();
                test.restockProduct(id, amount);
            }

    }
    StdOut.print(test.toString());
    }
}
