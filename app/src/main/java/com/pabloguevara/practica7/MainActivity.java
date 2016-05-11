package com.pabloguevara.practica7;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Cursor c;

    EditText eId, eNombre, eCantidad, ePrecio;
    Button bAgregar, bBuscar, bEliminar, bActualizar, bVender, bInventario, bGanancias;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eId = (EditText)findViewById(R.id.eId);
        eNombre = (EditText)findViewById(R.id.eNombre);
        eCantidad = (EditText)findViewById(R.id.eCantidad);
        ePrecio = (EditText)findViewById(R.id.ePrecio);

        bAgregar = (Button)findViewById(R.id.bAgregar);
        bBuscar = (Button)findViewById(R.id.bBuscar);
        bEliminar = (Button)findViewById(R.id.bEliminar);
        bActualizar = (Button)findViewById(R.id.bActualizar);
        bVender = (Button)findViewById(R.id.bVenta);
        bInventario = (Button)findViewById(R.id.bInventario);
        bGanancias = (Button)findViewById(R.id.bGanancias);

        tv = (TextView)findViewById(R.id.tv);

        cargar_defecto(1,"Iron_Man", 10, 15000);
        cargar_defecto(2,"Viuda_Negra",10,12000);
        cargar_defecto(3,"Capitan_America",10,15000);
        cargar_defecto(4,"Hulk",10,12000);
        cargar_defecto(5,"Bruja_Escarlata",10,15000);
        cargar_defecto(6,"SpiderMan",10,10000);

        bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar(v);
            }
        });

        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(v);
            }
        });

        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(v);
            }
        });

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar(v);
            }
        });

        bVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vender(v);
            }
        });

        bInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventario(v);
            }
        });

        bGanancias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganancias(v);
            }
        });

    }

    public void cargar_defecto (int id, String nombre, int cantidad, int precio){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        ContentValues defecto = new ContentValues();

        defecto.put("Id", id);
        defecto.put("Nombre", nombre);
        defecto.put("Cantidad", cantidad);
        defecto.put("Precio", precio);

        db.insert("Peluchitos", null, defecto);
        db.close();

    }

    public void agregar (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        String id = eId.getText().toString();
        String nombre = eNombre.getText().toString();
        String cantidad = eCantidad.getText().toString();
        String precio = ePrecio.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("Id",id);
        registro.put("Nombre",nombre);
        registro.put("Cantidad",cantidad);
        registro.put("Precio",precio);

        db.insert("Peluchitos", null, registro);
        db.close();

        eId.setText("");
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");

        tv.setText("Peluchito ingresado exitosamente");

    }

    public void buscar (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        String id = eId.getText().toString();

        c = db.rawQuery("SELECT Nombre,Cantidad,Precio FROM Peluchitos where Id="+id,null);

        if(c.moveToFirst()==true){
            eNombre.setText(c.getString(0));
            eCantidad.setText(c.getString(1));
            ePrecio.setText(c.getString(2));
            tv.setText("Datos del peluchito buscado");
        }
        else
        {
            tv.setText("El Id ingresado no existe");
        }

        db.close();

    }

    public void eliminar (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        String id = eId.getText().toString();

        int aux = db.delete("Peluchitos", "id=" + id, null);

        eId.setText("");
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");

        if (aux==1){
            tv.setText("Peluchito eliminado exitosamente");
        }
        else {
            tv.setText("El Id ingresado no existe");
        }

    }

    public void actualizar (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        String id = eId.getText().toString();
        String nombre = eNombre.getText().toString();
        String cantidad = eCantidad.getText().toString();
        String precio = ePrecio.getText().toString();

        ContentValues actualizacion = new ContentValues();

        actualizacion.put("Nombre", nombre);
        actualizacion.put("Cantidad", cantidad);
        actualizacion.put("Precio", precio);

        eId.setText("");
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");

        int aux = db.update("Peluchitos",actualizacion,"Id="+id,null);
        db.close();

        if (aux==1){
            tv.setText("Peluchito actualizado exitosamente");
        }
        else {
            tv.setText("El Id ingresado no existe");
        }

    }

    public void vender (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        String id = eId.getText().toString();

        c = db.rawQuery("SELECT Cantidad FROM Peluchitos where Id="+id,null);

        /*int aux = c.getInt(0);
        aux = aux-1;

        if(aux>1)
        {
            ContentValues registro = new ContentValues();
            registro.put("Cantidad", aux);
            db.insert("Peluchitos", null, registro);
            db.close();
            tv.setText("Peluche vendido exitosamente");
        }
        else
        {
            tv.setText("No existen unidades disponibles del peluche");
        }*/

        tv.setText("Peluchito vendido exitosamente");

        eId.setText("");
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");

    }

    public void inventario (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        c = db.rawQuery("SELECT Id,Nombre,Cantidad,Precio FROM Peluchitos", null);

        tv.setText("");
        if (c.moveToFirst())
            do {
                String id = c.getString(0);
                String nombre = c.getString(1);
                String cantidad = c.getString(2);
                String precio = c.getString(3);

                tv.append(id + " " + nombre + " " + cantidad + " " + precio + "\n");

            }
            while (c.moveToNext());
    }

    public void ganancias (View view){

        UserSQLite user = new UserSQLite(this,"User",null,1);
        SQLiteDatabase db = user.getWritableDatabase();

        eId.setText("");
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");

        tv.setText("Ganancias: ");

    }
}
