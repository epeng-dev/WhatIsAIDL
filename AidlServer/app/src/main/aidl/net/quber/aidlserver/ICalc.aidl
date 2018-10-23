// ICalc.aidl
package net.quber.aidlserver;

interface ICalc {
    int getLCM(in int a, in int b);
    boolean isPrime(in int n);
}
