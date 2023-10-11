package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        int sectorPlace = id%10;
        Product newProd = new Product(id, name, stock, day, demand);
        sectors[sectorPlace].add(newProd);

    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int sectorID = id%10;
        Sector sector = sectors[sectorID];
        int length = sector.getSize();
        sector.swim(length);
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int sectorID = id%10;
        Sector sector = sectors[sectorID];
        if(sector.getSize()==5)
        {
            sector.swap(1,sector.getSize());
            sector.deleteLast();
            sector.sink(1);
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int idKey = id%10;
        for(int i = 1; i <= sectors[idKey].getSize(); i++ )
        {
            if(sectors[idKey].get(i).getId() == id)
            {
                sectors[idKey].get(i).updateStock(amount);
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int idKey = id%10;
        Sector sector = sectors[idKey];
        for(int i = 1; i <= sector.getSize(); i++ )
        {
            if(sector.get(i).getId() == id)
            {
                sector.swap(i,sector.getSize());
                sector.deleteLast();
                sector.sink(i);
            }
        }
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int idKey = id%10;
        Sector sector = sectors[idKey];
        for(int i = 1; i <= sector.getSize(); i++ )
        {
            if(sector.get(i).getId() == id)
            {
                int stock = sector.get(i).getStock();
              
                if(stock>amount)
                {  
                    sector.get(i).setLastPurchaseDay(day);
                    sector.get(i).setStock(stock-amount); 
                    sector.get(i).updateDemand(amount);
                    sector.sink(i);
                }
               
            }
        }

    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        int idKey = id%10;
        int nextKey =0;
        int freeFound = 0;
        if(sectors[idKey].getSize() == 5)
        {
            for(int i = 0; i < 10; i++)
            {
                nextKey = (idKey+i)%10;
                if(sectors[nextKey].getSize() < 5)
                {
                    idKey=nextKey;
                    freeFound = 1;
                    break;
                }
            }
            if(freeFound == 1)
            {
                addProduct(idKey, name, stock, day, demand);
            }
            else
        {
            addProduct(idKey, name, stock, day, demand);
        }
        }
        else
        {
            addProduct(idKey, name, stock, day, demand);
        }
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
