/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.ast.types;

import java.util.Iterator;

/**
 *
 * @author regav
 */
public class Generator implements Iterator<Integer> {
    private int next = 2;
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public Integer next() {
        return next ++;
    }

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
