(defun gcd2 (num1 num2)
  (cond
   ((equal num2 0) num1)
   (t (gcd2 num2 (mod num1 num2)))
   )
)

(defun gcdInList (l c)
  (cond
   ((null l) c)
   ((atom l) l)
   ((atom (car l)) (gcdInList (cdr l) (gcd2 (car l) c)))
   (t (gcdInList (cdr l) (gcdInList (car l) c)))
   )
