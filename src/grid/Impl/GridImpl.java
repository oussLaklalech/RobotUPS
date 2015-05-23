package grid.Impl;

import grid.Grid;
import grid.Interfaces.ICellsInfo;

public class GridImpl extends Grid {

	@Override
	protected ICellsInfo make_cellsInfo() {
		
		return new ICellsInfo() {
			
			@Override
			public int getNbreCells() {
				
				return 10;
			}
		};
	}

}
