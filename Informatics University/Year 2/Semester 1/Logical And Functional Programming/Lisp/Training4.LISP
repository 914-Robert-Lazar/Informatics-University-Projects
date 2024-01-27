

(defun replaceEven (l)
  (cond
   ((and (numberp l) (equal (mod l 2) 0)) (+ l 1))
   ((atom l) l)
   (t (mapcar #'replaceEven l))
   )
)