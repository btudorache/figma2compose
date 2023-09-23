import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlin.Unit

@Composable
public fun List_page(): Unit {
  Column(verticalArrangement=Arrangement.SpaceAround, horizontalAlignment=Alignment.CenterHorizontally) {
    Text(text = "List page
        ", modifier = Modifier.size(width=92.23.dp, height=21.25.dp),
        fontSize = 20.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Label") }, supportingText = {
        Text("Supporting text") }, modifier = Modifier.size(width=331.00.dp, height=84.00.dp))
    Column() {
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
      Divider(thickness=4.dp)
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
      Divider(thickness=4.dp)
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
      Divider(thickness=4.dp)
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
      Divider(thickness=4.dp)
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
      Divider(thickness=4.dp)
      ListItem(headlineContent = { Text("headline text") },leadingContent = {
          Icon(Icons.Default.Person, contentDescription = "Localized description")
          },supportingContent = { Text("Supporting text") })
    }
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      FilledTonalButton(onClick = {}, 
      modifier = Modifier.size(width=129.00.dp, height=40.00.dp),
      colors = ButtonDefaults.buttonColors(containerColor=Color(red=0.40f, green=0.31f, blue=0.64f, alpha=1.00f))) {
        Text(text = "Add element", modifier = Modifier.size(width=79.80.dp, height=10.64.dp),
            fontSize = 12.sp, color = Color(red=1.00f, green=1.00f, blue=1.00f, alpha=1.00f))
      }
      FilledTonalButton(onClick = {}, 
      modifier = Modifier.size(width=155.00.dp, height=40.00.dp),
      colors = ButtonDefaults.buttonColors(containerColor=Color(red=0.40f, green=0.31f, blue=0.64f, alpha=1.00f))) {
        Text(text = "Remove element", modifier = Modifier.size(width=104.86.dp, height=10.64.dp),
            fontSize = 12.sp, color = Color(red=1.00f, green=1.00f, blue=1.00f, alpha=1.00f))
      }
    }
  }
}
