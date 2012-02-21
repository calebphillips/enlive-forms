* One place to add a new field
  Finish replacing validation with data ns
         Something is wrong in new fields map.
         The problem is view.clj line 50, which just creates an empty map
             Controller may need to call data/field-defaults and always pass something.
  Extract duplication from data ns
* Save application data
* get db config duplication out and make deployable to heroku
* Implement log in for employee to view submitted apps
* Add rating
