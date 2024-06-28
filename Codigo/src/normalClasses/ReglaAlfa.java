package normalClasses;

public class ReglaAlfa extends ReglaPractica {
    private String ValorCritico;
    private String ValorReservado;


    // CONSTRUCTOR DE LA REGLA_ALFA


    public ReglaAlfa(String valorCritico, String valorReservado) {
        ValorCritico = valorCritico;
        ValorReservado = valorReservado;
    }

    // GETTERS DE LA REGLA_ALFA


    public String getValorCritico() {
        return ValorCritico;
    }

    public String getValorReservado() {
        return ValorReservado;
    }

    // SETTERS DE LA REGLA_ALFA


    public void setValorCritico(String valorCritico) {
        ValorCritico = valorCritico;
    }

    public void setValorReservado(String valorReservado) {
        ValorReservado = valorReservado;
    }

    // METODO PARA VERIFICAR SI UN VALOR ALFA ES RESERVADO O CRITICO

    public String evaluarValor(String valor) {
        if (valor.equals(this.ValorReservado)) {
            return "Reservado";  // El valor coincide con el ValorReservado
        } else if (valor.equals(this.ValorCritico)) {
            return "Crítico";  // El valor coincide con el ValorCritico
        } else {
            return "Indefinido";  // El valor no coincide con ninguno de los dos
        }
    }

}

