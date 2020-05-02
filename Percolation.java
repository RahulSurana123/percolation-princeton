class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components

    /**
     * Initializes an empty union-find data structure with
     * {@code n} elements {@code 0} through {@code n-1}. 
     * Initially, each elements is in its own set.
     *
     * @param  n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param  p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    /**
     * Returns true if the two elements are in the same set.
     * 
     * @param  p one element
     * @param  q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    /**
     * Merges the set containing element {@code p} with the 
     * the set containing element {@code q}.
     *
     * @param  p one element
     * @param  q the other element
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}

public class Percolation {
	// creates n-by-n grid, with all sites initially blocked
	private boolean[][] tiles;
//	int upper_tile=1,lower_tile=0;
	private WeightedQuickUnionUF tile_parent_id,full_id;
//	boolean[][] site_status;
	private int open_tile_count;
	public Percolation(int n) {
//		int k=2;
    	tiles = new boolean[n][n];
    	tile_parent_id = new WeightedQuickUnionUF((n*n)+2);
    	full_id=new WeightedQuickUnionUF(n*n+1);
    	open_tile_count=0;
//    	site_status=new boolean[n][n];
//    	for(int i = 0; i < n; i++) {
//    		for(int j = 0; j < n; j++) {
//    			tiles[i][j]= false;
////    			site_status[i][j]=false;
////    			k++;
//    			
//    		}
//    	}
    }
//	public int root(int i,int j) {
////		int max_it=0;
////		int temp_max=0;
//		
//		while(tile_parent_id[i*tiles.length+j]!=i*tiles.length+j) {
////			temp_max=i;
//			
//			int k=(int)(tile_parent_id[i*tiles[0].length+j]%(tiles[0].length));
//			int l=(int)((tile_parent_id[i*tiles[0].length+j]-k)/tiles[0].length);
//			tile_parent_id[l*tiles.length+k]=tile_parent_id[tile_parent_id[l*tiles.length+k]];
////			if(max_it<=temp_max)
////				max_it=temp_max;
//		}
//		tree_size[i*tiles.length+j]++;
//		return i*tiles.length+j;
//	}
//	public void union(int r1,int c1,int r2,int c2) {
////		id[j]=i;              //This is lazy_union
//		
//		int i=root(r1-1, c1-1);
//		int j=root(r2-1, c2-1);
////		System.out.print("union r:"+i+" "+j+"\n");
//		if(tiles[r1-1][c1-1] && isOpen(r2, c2))
//			tiles[r2-1][c2-1]=true;							//**********  //**********
//		else if(tiles[r2-1][c2-1] && isOpen(r1, c1))
//			tiles[r1-1][c1-1]=true;//**********// weighted lazy_union							//***********  //*********
//		if(tree_size[(r1-1)*tiles.length+(c1-1)]>tree_size[(r2-1)*tiles.length+(c2-1)]){
//			tile_parent_id[j]=i;							//**********
////			System.out.print(i+"papa"+j+"\n");  //**********
//			}									//**********
//		else {									// weighted lazy_union
//			tile_parent_id[i]=j;							//***********
////			System.out.print(j"papa"+i+"\n");  //**********
//			}
////		System.out.print(tile_parent_id[i]+"papa"+tile_parent_id[j]+"\n");
//	}
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	validateSite(row, col);
    	tiles[row-1][col-1] = true;
    	open_tile_count++;
//    	print();
    	connection_maker(row, col);
    	
    }
//    public int count() {
//    	return open_tile_count;
//    } 
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	validateSite(row, col);
    	return tiles[row-1][col-1];
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	validateSite(row, col);
    	return full_id.connected(tiles.length*tiles.length,parent_id(row, col)-1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return open_tile_count;
    }
    
    // does the system percolate?
    public boolean percolates() {
		return (tile_parent_id.connected(0,parent_id(tiles.length,(tiles.length)) + 1));
    }
    private int parent_id(int row, int col) {
        return tiles.length * (row - 1) + col;
    }
    private void connection_maker(int row,int col) {
    	int id = parent_id(row,col);
//    	print();
    if (row == 1) {
		tile_parent_id.union(0, id);
		full_id.union(tiles.length*tiles.length, id-1);
	}
	if (row == tiles.length) {
		tile_parent_id.union(parent_id(tiles.length, tiles.length) + 1, id);
	}
	if(row >= 2 && isOpen(row-1, col)) { 
//		System.out.print("union no up:"+(row-1)+" "+col+"\n");
		tile_parent_id.union(parent_id( row-1,col), id);
		full_id.union(parent_id( row-1,col)-1, id-1);
		}
	if(row < tiles.length && isOpen(row+1, col)) {
//		System.out.print("union no down:"+(row+1)+" "+col+"\n");
		tile_parent_id.union(parent_id( row+1, col), id);
		full_id.union(parent_id( row+1, col)-1, id-1);
		}
	if(col >= 2 && isOpen(row, col-1)) {
//		System.out.print("union no left:"+row+" "+(col-1)+"\n");
		tile_parent_id.union(parent_id( row, col - 1), id);
		full_id.union(parent_id( row, col - 1)-1, id-1);
		}
	if(col < tiles.length&& isOpen(row, col + 1)) {
//		System.out.print("union no right:"+row+" "+(col+1)+"\n");
		tile_parent_id.union(parent_id(row, col + 1),id);
		full_id.union(parent_id(row, col + 1)-1, id-1);
		}
    }
    private void validateSite(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private boolean isOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < tiles.length && shiftCol < tiles.length);
    }
}
