package normalClasses;

public class ReglaNumerica extends ReglaPractica {
    private float ValorCritico;
    private float ValorReservado;


    // CONSTRUCTOR DE LA REGLA NUMERICA

    public ReglaNumerica(float valorCritico, float valorReservado) {
        // Asegúrate de que valorReservado sea menor o igual que valorCritico
        if (valorReservado <= valorCritico) {
            this.ValorCritico = valorCritico;
            this.ValorReservado = valorReservado;
        } else {
            throw new IllegalArgumentException("ValorReservado debe ser menor o igual que ValorCritico.");
        }
    }


    // GETTERS DE LA REGLA NUMERICA


    public float getValorReservado() {
        return ValorReservado;
    }

    public float getValorCritico() {
        return ValorCritico;
    }


    // SETTERS DE LA REGLA NUMERICA


    public void setValorCritico(float valorCritico) {
        if (this.ValorReservado <= valorCritico) {
            this.ValorCritico = valorCritico;
        } else {
            throw new IllegalArgumentException("ValorReservado debe ser menor o igual que ValorCritico.");
        }
    }

    public void setValorReservado(float valorReservado) {
        if (valorReservado <= this.ValorCritico) {
            this.ValorReservado = valorReservado;
        } else {
            throw new IllegalArgumentException("ValorReservado debe ser menor o igual que ValorCritico.");
        }
    }

    // METODO PARA VERIFICAR SI UN METODO ES RESERVADO O CRITICO

    public String evaluarValor(float valor) {
        if (valor >= this.ValorCritico) {
            return "Crítico";  // El valor es igual o mayor que el ValorCritico
        } else if (valor >= this.ValorReservado && valor < this.ValorCritico) {
            return "Reservado";  // El valor está entre el ValorReservado y el ValorCritico (exclusivo)
        } else {
            return "Indefinido";  // El valor no cae en ninguna de las categorías anteriores
        }
    }

}
