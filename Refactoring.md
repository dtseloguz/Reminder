## Переименование метода

### 1 Предпосылки для прохождения рефакторинга
Метод delete получил неудачное название так как создавался впопыхах, и его название не отображает суть того, что он делает.

### 2 Переименование метода
Улучшает читабельность кода.  

### 3 Реализация 

Чтобы сохранить работоспособность программы во время рефакторинга. 
создаем новый метод с новыми именем. Копируем туда код старого метода. 
Удаляем весь код в старом методе и вставьте вместо него вызов нового метода.

      protected void delete(final int pos){    
       deleteItemFromDatabase(pos);    
      }    

      protected void deleteItemFromDatabase(final int pos){
          ad = new AlertDialog.Builder(MainActivity.this);
          //ad.setTitle(title);  // заголовок
          ad.setMessage("Delete?"); // сообщение
          ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int arg1) {
                  cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
                  cursor.moveToPosition(pos);
                  int idColIndex=cursor.getColumnIndex(DB.COLUMN_ID);
                  db.delete(DB.TABLE_NAME, "_id = " + Integer.parseInt(cursor.getString(0)), null);
                  cursor.close();
                  adapter.clear();
                  show();
              }
          });
          ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int arg1) {
      
              }
          });
          ad.show();

       }     
    
### 4 Вывод
Улучшает понимание и организацию кода. 

## Мертвый код

### 1 Предпосылки для прохождения рефакторинга
Переменная, параметр, поле, метод или класс больше не используются.

### 2 Реализация 
Выделенные переменный не используются
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);
       ** nameEt = findViewById(R.id.editTextName); **
       ** dateEt = findViewById(R.id.btnDate); **
       ** timeEt = findViewById(R.id.editTextTime); **
        DB=new DBHelper(this); 
        OkBtn=findViewById(R.id.buttonOK2);
        cal = Calendar.getInstance();
    }
Результат:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);
        DB=new DBHelper(this); 
        OkBtn=findViewById(R.id.buttonOK2);
        cal = Calendar.getInstance();
    }
    
### 4 Вывод
Улучшает понимание и организацию кода. 
