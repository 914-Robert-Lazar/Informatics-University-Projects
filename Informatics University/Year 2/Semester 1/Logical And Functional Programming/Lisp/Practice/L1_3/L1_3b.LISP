(defun reverseLinear (l)
  (cond
   ((null l) nil)
   ((atom (car l)) (append (reverseLinear (cdr l)) (list (car l))))
   (t (append (reverseLinear (cdr l)) (reverseLinear (car l))))
   )
)