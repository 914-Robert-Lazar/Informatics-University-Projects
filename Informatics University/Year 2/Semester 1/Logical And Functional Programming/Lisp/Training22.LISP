(defun g3(l)
  (list (car l) (car l))
)

(defun replaceOdd (l e n)
  (cond
   ((and (atom l) (= (mod n 2) 1)) e)
   ((atom l) l)
   (t (mapcar #'(lambda (a) (replaceOdd a e (+ n 1))) l))
   )
)

(defun replaceOdds (l e)
  (replaceOdd l e -1)
)