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
     * Current operation.
     */
    private DoubleBinaryOperator currOperation;

    /**
     * Currently displayed result.
     */
    private String displayedValue;

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
    private Double currentOperand;

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
        this.editable = false;
        if(value < 0){
            this.negative = true;
            value *= -1;
        }
        this.inputValue = value;
        this.input = inputValue.toString();
        this.displayedValue = null;
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
        this.editable = true;
        this.inputValue = 0.;
        this.input = null;
        this.negative = false;
    }

    /**
     * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
     * operand i zakazanu operaciju.
     */
    @Override
    public void clearAll() {
        this.clear();
        this.currentOperand = null;
        this.currOperation = null;
        this.displayedValue = null;
    }

    /**
     * Mijenja predznak unesenog broja.
     * @throws CalculatorInputException ako kalkulator nije editabilan
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if(!this.editable)
            throw new CalculatorInputException("Calculator is not editable!");

        this.negative = !this.negative;
        this.displayedValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Dodaje na kraj trenutnog broja decimalnu točku.
     * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
     * ako broj već sadrži decimalnu točku ili ako kalkulator nije editabilan
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if(!isEditable() || this.input == null ||this.input.contains("."))
            throw new CalculatorInputException("Can't place decimal point!");

        if(this.input.isEmpty()){
            this.input = "0.";
        } else {
            input += ".";
        }
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
        if(!this.editable)
            throw new CalculatorInputException("Calculator is not editable!");
        if(digit < 0 || digit > 9)
            throw new IllegalArgumentException("Only single digit, positive numbers can be passed as arguments!");
        if(input.isEmpty()){
            input = String.valueOf(digit);
        } else {
            String newInput = input + String.valueOf(digit);
            try{
                inputValue = Double.parseDouble(newInput);
            } catch (NumberFormatException ignored){
                throw new CalculatorInputException("Can't parse given number!");
            }
            listeners.forEach(l -> l.valueChanged(this));
        }
    }

    /**
     * Provjera je li upisan aktivni operand.
     * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inače
     */
    @Override
    public boolean isActiveOperandSet() {
        return this.currentOperand != null;
    }

    /**
     * Dohvat aktivnog operanda.
     * @return aktivni operand
     * @throws IllegalStateException ako aktivni operand nije postavljen
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        return this.currentOperand;
    }

    /**
     * Metoda postavlja aktivni operand na predanu vrijednost.
     * Ako kalkulator već ima postavljen aktivni operand, predana
     * vrijednost ga nadjačava.
     * @param activeOperand vrijednost koju treba pohraniti kao aktivni operand
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.currentOperand = activeOperand;
        this.editable = false;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Uklanjanje zapisanog aktivnog operanda.
     */
    @Override
    public void clearActiveOperand() {
        this.currentOperand = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Dohvat zakazane operacije.
     * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return currOperation;
    }

    /**
     * Postavljanje zakazane operacije. Ako zakazana operacija već
     * postoji, ovaj je poziv nadjačava predanom vrijednošću.
     * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        this.currOperation = op;
        listeners.forEach(l -> l.valueChanged(this));
    }

    @Override
    public String toString(){
        if(displayedValue == null || input.equals("0") || input.isEmpty()){
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        if(negative)
            sb.append("-");
        sb.append(input);

        return sb.toString();
    }
}
