# Formulas
[Volver](README.md)

**Calcular la posicion en X,Y**

La posicion en X la obtenemos multiplicando el Radio (Distancia del planeta al sol) por el coseno de la velocidad angular en radianes por tiempo. Ej:

r = radio
w = velocidad angular
t = tiempo

pos x = r * coseno( w * t) 

La posicion en Y la obtenemos multiplicando el Radio (Distancia del planeta al sol) por el seno de la velocidad angular en radianes por tiempo. Ej:

r = radio
w = velocidad angular
t = tiempo

pos y = r * seno( w * t)

**Calcular si el sol se encuentra dentro del triangulo formado por los tres planetas**

Dados 3 puntos (A,B,C) crearemos un triangulo y debemos saber si un punto P se encuentra dentro del mismo triangulo.
El calculo se hara formando triangulos con 2 de los puntos del triangulo original y el punto a identificar dentro del mismo triangulo.
Para realizarlo se usa la siguente formula
P1 = Punto 1 
P2 = Punto 2
P3 = Punto 3
Ej: P1x es el valor de x en el punto 1
    P1y es el valor de y en el punto 1

Resultado = (P1x-P3x) X (P2y-P3y)-(P1y-P3y) X (P2x-P3x)

Se contempla el Resultado de los triangulos (A,B,C),(A,B,P),(B,C,P)y(C,A,P)
Si el signo resultante de los 4 triangulos es positivo, el punto P esta dentro del triangulo (A,B,C).
Si el signo resultante de los 4 triangulos es negativo, el punto P esta dentro del triangulo (A,B,C).
Si el sigo resultante de alguno de los 4 es diferente al signo del resto, el punto P no está dentro del triangulo (A,B,C).

**Calcular si los tres planetas estan alineados**

Dadas las coordenadas en x e y de dos planetas (P1, P2) podremos calcular la pendiente de la siguiente manera:

pendiente  = (P2y-P1y)/(P2x-P1x)

Una vez obtenida la pendiente podemos pasar a usar la ecuacion de la recta para verificar si un punto (P3) pertenece a una recta.

Ecuacion de la recta (P3y-P1y) == pendiente X (P3x-P1x)
Si estos valores son iguales el punto P3 pertenece a la recta, o sea esta alineado.

Otra forma es verificar la igualdad entre dos pendientes (P1,P2) y (P3,Sol) para asegurar que estan en la misma recta.
Dado que en el ejercicio no hay muchos ejemplos de extrema exactitud se tomo como medida usar una tolerancia de 20Km para la diferencia entre valores a comprarar.



