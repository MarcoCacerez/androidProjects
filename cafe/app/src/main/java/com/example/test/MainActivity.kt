package com.example.test
//Marco Aldair de Jesus Caceres 18390579 ISC 8U
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOrder.setOnClickListener{
            calculatePrice()
        }
    }
    //Funcion encargada de enviar los mensajes en caso de que falte algun dato
    fun sendMsg(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
        return
    }

    fun calculatePrice(){
        //obtenemos la cantidad de bebidas a pedir
        val quantity = binding.fldQuantity.text.toString().toIntOrNull()
        //en caso de que no se ingrese la cantidad enviamos un mensaje
        if(quantity == null){
            sendMsg("Ingrese la cantidad")
            return
        }

        //Aqui obtenemos el tipo de bebida a pedir
        val coffeeType = binding.radioGroup.checkedRadioButtonId
        //Verificamos cual se pidio para asignar un precio a la variable cost
        val cost = when(coffeeType){
            R.id.rdbTe -> 20
            R.id.rdbColdBrew -> 25
            R.id.rbEspresso -> 30
            else -> {
                //en caso de que no seleccione ninguna, mostramos un mensaje
                sendMsg("Seleccione un tipo de cafe")
                return
            }
        }
        //lo mismo para el tipo el tamano de la bebida
        val coffeeSizeId = binding.rgCoffeeSize.checkedRadioButtonId
        var coffeeSizePrice = when(coffeeSizeId){
            R.id.rbGrande -> cost * .10
            R.id.rbTall -> cost * .15
            R.id.rbVenti -> cost * .20
            R.id.rbTrenta -> cost * .25
            else -> {
                sendMsg("Elija el tamaño")
                return
            }
        }
        //Multiplicamos el precio del tamano por la cantidad de bebidas pedidas
        coffeeSizePrice *= quantity

        //seleccionar el tipo de leche
        val milkType = binding.rgMilk.checkedRadioButtonId
        var milkPrice = when(milkType){
            R.id.rbAlmondMilk -> 10
            R.id.rbMilk -> 5
            R.id.rbNoMilk -> 0
            else -> {
                sendMsg("Seleccione un tipo de leche")
                return
            }
        }
        //Multiplicamos el costo del tipo de leche por la cantidad de bebidas
        milkPrice *= quantity

        //creamos esta variable para almacenar el costo del azucar
        var adicional = 0
        val sugar = binding.swtAzucar.isChecked
        if (sugar){
            //en caso de que tenga activado el switch se multiplica la cantidad por el precio del azucar
            adicional = 5 * quantity
        }
        val subTotal = quantity * cost
        val total = subTotal + adicional + milkPrice + coffeeSizePrice
        val fTotal = NumberFormat.getCurrencyInstance().format(total)
        binding.tvTotal.text = getString(R.string.total,fTotal)


    }
}