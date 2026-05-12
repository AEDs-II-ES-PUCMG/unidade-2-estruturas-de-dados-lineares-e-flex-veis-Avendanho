import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lista<E> implements Iterable<E> {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int tamanho;

    public Lista() {
        Celula<E> sentinela = new Celula<>();
        primeiro = ultimo = sentinela;
        tamanho = 0;
    }

    public boolean vazia() {
        return primeiro == ultimo;
    }

    public int tamanho() {
        return tamanho;
    }

    public void inserirFinal(E item) {
        Celula<E> nova = new Celula<>(item);
        ultimo.setProximo(nova);
        ultimo = nova;
        tamanho++;
    }

    public void inserirInicio(E item) {
        Celula<E> nova = new Celula<>(item, primeiro.getProximo());
        if (vazia()) ultimo = nova;
        primeiro.setProximo(nova);
        tamanho++;
    }

    public E removerInicio() {
        if (vazia()) throw new NoSuchElementException("Lista vazia!");
        Celula<E> primeiro = this.primeiro.getProximo();
        primeiro.setProximo(primeiro.getProximo());
        if (primeiro == ultimo) ultimo = primeiro;
        primeiro.setProximo(null);
        tamanho--;
        return primeiro.getItem();
    }

    public void imprimir() {
        if (vazia()) {
            System.out.println("A lista está vazia!");
        } else {
            Celula<E> aux = primeiro.getProximo();
            while (aux != null) {
                System.out.println(aux.getItem());
                aux = aux.getProximo();
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Celula<E> atual = primeiro.getProximo();

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E item = atual.getItem();
                atual = atual.getProximo();
                return item;
            }
        };
    }

    public E pegar(int posicao) {
        if(posicao >= tamanho)
            throw new IndexOutOfBoundsException();

        Celula<E> atual = primeiro.getProximo();
        for(int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }

        return atual.getItem();
    }

    public void inserir(E item, int posicao) {
        Celula<E> antecessor = primeiro;

        Celula<E> sucessor = primeiro.getProximo();
        for(int i = 0; i < posicao; i++) {
            antecessor = sucessor;
            sucessor = sucessor.getProximo();
        }

        Celula<E> novo = new Celula<>(item);
        antecessor.setProximo(novo);
        novo.setProximo(sucessor);
    }

    // Tarefa 1
    public E buscarPor(Comparator<E> criterioDeBusca, E item) {
        E resultado = null;
        
        Celula<E> atual = primeiro;
        while(atual != ultimo) {
            System.out.println(atual.getItem());
            if(criterioDeBusca.compare(atual.getItem(), item) == 0) {
                resultado = item;
                atual = ultimo;
            }
            atual = atual.getProximo();
        }

        return resultado;
    }

    // Tarefa 2
    public double somarMultiplicacoes(Function<E, Double> extratorValor, Function<E, Integer> extratorFator) {
        double soma = 0d;

        Celula<E> atual = primeiro;
        while(atual != null) {
            double fator = extratorFator.apply(atual.getItem());
            double valor = extratorValor.apply(atual.getItem());
            soma += valor * fator;
            atual = atual.getProximo();
        }

        return soma;
    }

    // Tarefa 3
    public Lista<E> filtrar(Predicate<E> condicional) {
        if(tamanho == 0) {
            throw new IllegalStateException();
        }
        
        Lista<E> resultante = new Lista<>();

        Celula<E> atual = primeiro.getProximo();
        while(atual != ultimo) {
            if(condicional.test(atual.getItem())) {
                resultante.inserirFinal(atual.getItem());
            }
            atual = atual.getProximo();
        }

        return resultante;
    }
}
