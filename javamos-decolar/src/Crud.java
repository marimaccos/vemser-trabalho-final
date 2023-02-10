package javamos_decolar;

public interface Crud<T> {

    void adicionar(T t);
    void listar();
    void editar(Integer index, T t);
    void remover(Integer index);
}
