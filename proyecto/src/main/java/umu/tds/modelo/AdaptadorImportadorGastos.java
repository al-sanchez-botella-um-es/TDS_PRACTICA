package umu.tds.modelo;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AdaptadorImportadorGastos {
	List<Gasto> importar(File archivo) throws IOException;
}