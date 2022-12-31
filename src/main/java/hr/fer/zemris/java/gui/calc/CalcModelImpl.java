package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of {@link CalcModel} interface.
 * @author MatijaPav
 */
public class CalcModelImpl implements CalcModel {
    /**
     * List of {@code CalcValueListeners}
     */
    private List<CalcValueListener> listeners;

    /**
     * Pending operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * Currently displayed result.
     */
    private String frozenValue;

    /**
     * Input.
     */
    private String input;

    /**
     * Double value of currently input digits.
     */
    private Double inputValue;

    /**
     * Indicates the editability of model.
     */
    private boolean editable;

    /**
     * Current operand.
     */
    private Double activeOperand;

    /**
     * Indicates if the number is negative.
     */
    private boolean negative;

    public CalcModelImpl(){
        this.listeners = new ArrayList<>();
        this.negative = false;
        this.editable = true;
        this.input = "";
        this.inputValue = 0.;
    }

    /**
     * Prijava promatrača koje treba obavijestiti kada se
     * promijeni vrijednost pohranjena u kalkulatoru.
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l, "Listener can't be null!");
        this.listeners.add(l);
    }

    /**
     * Odjava promatrača s popisa promatrača koje treba
     * obavijestiti kada se promijeni vrijednost
     * pohranjena u kalkulatoru.
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l, "Listener can't be null!");
        this.listeners.remove(l);
    }

    /**
     * Vraća trenutnu vrijednost koja je pohranjena u kalkulatoru.
     * @return vrijednost pohranjena u kalkulatoru
     */
    @Override
    public double getValue() {
        if(negative)
            return -1 * this.inputValue;
        return this.inputValue;
    }

    /**
     * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
     * biti i beskonačno odnosno NaN. Po upisu kalkulator
     * postaje needitabilan.
     * @param value vrijednost koju treba upisati
     */
    @Override
    public void setValue(double value) {
        this.negative = value < 0;
        this.inputValue = Math.abs(value);
        this.input = inputValue.toString();
        this.frozenValue = null;
        this.editable = false;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Vraća informaciju je li kalkulator editabilan (drugim riječima,
     * smije li korisnik pozivati metode {@link #swapSign()},
     * {@link #insertDecimalPoint()} te {@link #insertDigit(int)}).
     * @return <code>true</code> ako je model editabilan, <code>false</code> inače
     */
    @Override
    public boolean isEditable() {
        return this.editable;
    }

    /**
     * Resetira trenutnu vrijednost na neunesenu i vraća kalkulator u
     * editabilno stanje.
     */
    @Override
    public void clear() {
        this.inputValue = 0.;
        this.input = "";
        this.frozenValue = null;
        this.editable = true;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
     * operand i zakazanu operaciju.
     */
    @Override
    public void clearAll() {
        this.activeOperand = null;
        this.pendingOperation = null;
        this.clear();
    }

    /**
     * Mijenja predznak unesenog broja.
     * @throws CalculatorInputException ako kalkulator nije editabilan
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if(!this.isEditable())
            throw new CalculatorInputException("Calculator is not editable!");
        this.negative = !negative;
        this.frozenValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Dodaje na kraj trenutnog broja decimalnu točku.
     * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
     * ako broj već sadrži decimalnu točku ili ako kalkulator nije editabilan
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if(!isEditable() || this.input.isEmpty() || this.input.contains("."))
            throw new CalculatorInputException("Can't place decimal point!");

        this.input += ".";
        this.inputValue = Double.parseDouble(input);
        this.frozenValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * U broj koji se trenutno upisuje na kraj dodaje poslanu znamenku.
     * Ako je trenutni broj "0", dodavanje još jedne nule se potiho
     * ignorira.
     * @param digit znamenka koju treba dodati
     * @throws CalculatorInputException ako bi dodavanjem predane znamenke broj postao prevelik za konačan prikaz u tipu {@link Double}, ili ako kalkulator nije editabilan.
     * @throws IllegalArgumentException ako je <code>digit &lt; 0</code> ili <code>digit &gt; 9</code>
     */
    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if(digit < 0 || digit > 9)
            throw new IllegalArgumentException("Only single digit, positive numbers can be passed as arguments!");
        if(!this.editable)
            throw new CalculatorInputException("Calculator is not editable!");

        String s = (this.input + digit);
        s = s.replaceFirst("^0+(?!(\\.|$))", "");

        double newValue = Double.parseDouble(s);
        if(Double.isNaN(newValue) || Double.isInfinite(newValue))
            throw new CalculatorInputException("The new value cannot be NaN or infinite!");

        this.input = s;
        this.inputValue = newValue;
        this.frozenValue = null;
        listeners.forEach(l -> l.valueChanged(this));

    }

    /**
     * Provjera je li upisan aktivni operand.
     * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inače
     */
    @Override
    public boolean isActiveOperandSet() {
        return this.activeOperand != null;
    }

    /**
     * Dohvat aktivnog operanda.
     * @return aktivni operand
     * @throws IllegalStateException ako aktivni operand nije postavljen
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        if(activeOperand == null)
            throw new IllegalStateException();
        return this.activeOperand;
    }

    /**
     * Metoda postavlja aktivni operand na predanu vrijednost.
     * Ako kalkulator već ima postavljen aktivni operand, predana
     * vrijednost ga nadjačava.
     * @param activeOperand vrijednost koju treba pohraniti kao aktivni operand
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    /**
     * Uklanjanje zapisanog aktivnog operanda.
     */
    @Override
    public void clearActiveOperand() {
        this.activeOperand = null;
    }

    /**
     * Dohvat zakazane operacije.
     * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    /**
     * Postavljanje zakazane operacije. Ako zakazana operacija već
     * postoji, ovaj je poziv nadjačava predanom vrijednošću.
     * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        this.pendingOperation = op;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(negative)
            sb.append("-");
        if(input.isEmpty()){
            sb.append("0");
        }
        sb.append(input);

        return sb.toString();
    }
}
