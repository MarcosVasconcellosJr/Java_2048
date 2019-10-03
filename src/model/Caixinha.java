package model;

public class Caixinha {
    public boolean mesclada;
    public int valor;

    public Caixinha(int val) {
        valor = val;
    }
    public void Caixinha(int val){
        valor = val;
    }
    public int pegaValor() {
        return valor;
    }

    public void setMesclada(boolean m) {
        mesclada = m;
    }

    public boolean podeMesclarCom(Caixinha proxima) {
        if(!mesclada && proxima != null && !proxima.mesclada && valor == proxima.pegaValor()){
            return true;
        } 
        else {
            return false;
        }
    }

    public int mesclarCom(Caixinha proxima) {
        if (podeMesclarCom(proxima)) {
            valor *= 2;
            mesclada = true;
            return valor;
        }
        return -1;
    }
}