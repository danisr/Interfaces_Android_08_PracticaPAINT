package com.utad.dint08_practicapaint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.UUID;

public class DibujarEnPantallaActivity extends Activity {
	PizarraView pizarraView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dibujar_en_pantalla);

		ImageButton nuevo = (ImageButton) findViewById(R.id.nuevo);
		ImageButton borrar = (ImageButton) findViewById(R.id.borrar);
		ImageButton guardar = (ImageButton) findViewById(R.id.guardar);

		ImageView negro = (ImageView) findViewById(R.id.ivNegro);
		ImageView blanco = (ImageView) findViewById(R.id.ivBlanco);
		ImageView rojo = (ImageView) findViewById(R.id.ivRojo);
		ImageView azul = (ImageView) findViewById(R.id.ivAzul);
		ImageView verde = (ImageView) findViewById(R.id.ivVerde);
		ImageView morado = (ImageView) findViewById(R.id.ivMorado);
		ImageView naranja = (ImageView) findViewById(R.id.ivNaranja);
		ImageView celeste = (ImageView) findViewById(R.id.ivCeleste);

		SeekBar grosor = (SeekBar) findViewById(R.id.sbGrosor);

		pizarraView = (PizarraView) findViewById(R.id.lienzo);

		// Menú superior (nuevo, borrar, guardar)
		final View.OnClickListener clickListenerMenu = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (view.getId()){
                    //Nuevo dibujo
					case R.id.nuevo:
						AlertDialog.Builder newDialog = new AlertDialog.Builder(DibujarEnPantallaActivity.this);
						newDialog.setTitle("Nuevo Dibujo");
						newDialog.setMessage("¿Iniciar un nuevo dibujo?");
						newDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which){
								pizarraView.nuevoDibujo();
								dialog.dismiss();
							}
						});
						newDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which){
								dialog.cancel();
							}
						});
						newDialog.show();
						break;
                    //Borrar lo pintado
					case R.id.borrar:
						pizarraView.borrarPintado(true);
						break;
                    //Guardar dibujo
					case R.id.guardar:
                        AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(DibujarEnPantallaActivity.this);
                        salvarDibujo.setTitle("Guardar dibujo");
                        salvarDibujo.setMessage("¿Guardar dibujo en galeria?");
                        salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                //Guardar dibujo
                                pizarraView.setDrawingCacheEnabled(true);

                                String imgSaved = MediaStore.Images.Media.insertImage(
                                        getContentResolver(), pizarraView.getDrawingCache(),
                                        UUID.randomUUID().toString()+".png", "drawing");

                                if(imgSaved != null) {
                                    Toast savedToast = Toast.makeText(getApplicationContext(),
                                            "¡Dibujo guardado en la galeria!", Toast.LENGTH_SHORT);
                                    savedToast.show();
                                }
                                else{
                                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                            "¡Error! El dibujo no se ha podido guardar", Toast.LENGTH_SHORT);
                                    unsavedToast.show();
                                }
                                pizarraView.destroyDrawingCache();
                            }
                        });
                        salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        });
                        salvarDibujo.show();
						break;

                    default:
                        break;
				}
			}
		};
		nuevo.setOnClickListener(clickListenerMenu);
		borrar.setOnClickListener(clickListenerMenu);
		guardar.setOnClickListener(clickListenerMenu);

		// Modificar colores
		View.OnClickListener clickListenerColores = new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				int color = ((ColorDrawable)view.getBackground()).getColor();
				pizarraView.cambiarColor(color);
			}
		};
		negro.setOnClickListener(clickListenerColores);
		blanco.setOnClickListener(clickListenerColores);
		rojo.setOnClickListener(clickListenerColores);
		azul.setOnClickListener(clickListenerColores);
		verde.setOnClickListener(clickListenerColores);
		morado.setOnClickListener(clickListenerColores);
		naranja.setOnClickListener(clickListenerColores);
		celeste.setOnClickListener(clickListenerColores);

		// Grosor del pincel
		grosor.setMax(20);
		grosor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				pizarraView.cambiarGrosor(i);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dibujar_en_pantalla, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}