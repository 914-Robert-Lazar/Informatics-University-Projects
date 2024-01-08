
;a) Write a function to return the product of two vectors.

(defun dotProduct (l1 l2)
  (cond
   ((null l1) 0)
   (t (+ (* (car l1) (car l2)) (dotProduct(cdr l1) (cdr l2))))
  )
)

