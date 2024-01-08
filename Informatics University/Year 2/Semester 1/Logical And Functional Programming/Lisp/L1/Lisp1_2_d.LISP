;d) Write a function to return the intersection of two sets.

(defun contains (l e)
  (cond 
   ((null l) 0)
   ((= (car l) e) 1)
   (t (contains (cdr l) e))
  )
)

(defun intersectionOfSets (l1 l2)
  (cond 
   ((null l1) nil)
   ((= (contains l2 (car l1)) 1)  (cons (car l1) (intersectionOfSets (cdr l1) l2)))
    (t (intersectionOfSets (cdr l1) l2))
   )
)