import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlin.Unit

@Composable
public fun Settings_page(): Unit {
  Column(verticalArrangement=Arrangement.SpaceAround, horizontalAlignment=Alignment.CenterHorizontally) {
    Text(text = "Settings
        ", modifier = Modifier.size(width=83.47.dp, height=21.25.dp),
        fontSize = 20.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    Text(text = "Customization", modifier = Modifier.size(width=116.11.dp, height=13.31.dp),
        fontSize = 16.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Activate night mode", modifier = Modifier.size(width=132.85.dp, height=13.42.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Switch(checked=true,onCheckedChange={})
    }
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Change language", modifier = Modifier.size(width=113.85.dp, height=13.42.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Text(text = "English", modifier = Modifier.size(width=46.22.dp, height=13.42.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    }
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Change theme", modifier = Modifier.size(width=94.52.dp, height=13.42.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Text(text = "Classic", modifier = Modifier.size(width=47.06.dp, height=10.64.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    }
    Text(text = "Settings", modifier = Modifier.size(width=65.90.dp, height=16.88.dp),
        fontSize = 16.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Sync with email
          account", modifier = Modifier.size(width=161.04.dp, height=13.49.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Switch(checked=true,onCheckedChange={})
    }
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Edit profile", modifier = Modifier.size(width=69.56.dp, height=13.49.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Text(text = "@account_name", modifier = Modifier.size(width=108.99.dp, height=12.88.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    }
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Delete account", modifier = Modifier.size(width=98.07.dp, height=10.64.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Switch(checked=true,onCheckedChange={})
    }
    FilledTonalButton(onClick = {}, 
    modifier = Modifier.size(width=139.00.dp, height=40.00.dp),
    colors = ButtonDefaults.buttonColors(containerColor=Color(red=0.40f, green=0.31f, blue=0.64f, alpha=1.00f))) {
      Text(text = "Apply settings", modifier = Modifier.size(width=89.96.dp, height=13.49.dp),
          fontSize = 12.sp, color = Color(red=1.00f, green=1.00f, blue=1.00f, alpha=1.00f))
    }
  }
}
