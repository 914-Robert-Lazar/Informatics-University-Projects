;b) Write a function to return the depth of a list. Example: the depth of a linear list is 1.

(defun depth (l m curr)
  (if (> curr m)
      (setq m curr))
  (cond
   ((null l) m)
   ((not (listp l)) 0)
   ((listp (car l))
    (progn
      (setq m (max m (depth (car l) m (+ curr 1))))
      (setq m (max m (depth (cdr l) m curr)))
    )
   )
   (t (setq m (max m (depth (cdr l) m curr))))
  )
)