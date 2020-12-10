package org.legd.sweatworksdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import org.legd.sweatworksdemo.R
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.app.SweatworksApplication
import org.legd.sweatworksdemo.database.models.User
import org.legd.sweatworksdemo.viewmodel.UserViewModelFactory
import org.legd.sweatworksdemo.viewmodel.UsersViewModel

class UserDetails : AppCompatActivity() {

    private var selectedUser: ApiUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_details)

        val userPicture = findViewById<ImageView>(R.id.user_picture)
        val name = findViewById<TextView>(R.id.name)
        val userName = findViewById<TextView>(R.id.username)
        val email = findViewById<TextView>(R.id.email)
        val address = findViewById<TextView>(R.id.address)
        val phoneNumber = findViewById<TextView>(R.id.phone_number)
        val cellNumber = findViewById<TextView>(R.id.cell_number)

        val addContact = findViewById<FloatingActionButton>(R.id.add_contact)
        addContact.setOnClickListener(View.OnClickListener {
            val addContactIntent = Intent(Intent.ACTION_INSERT)
            addContactIntent.type = ContactsContract.Contacts.CONTENT_TYPE

            addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name.text.toString())
            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber.text.toString())
            addContactIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, cellNumber.text.toString())

            startActivity(addContactIntent)
        })

        val addFavorite = findViewById<FloatingActionButton>(R.id.add_favorite)
        addFavorite.setOnClickListener(View.OnClickListener {
            val usersModel: UsersViewModel by viewModels {
                UserViewModelFactory((application as SweatworksApplication).userRepository)
            }
//            val usersModel: UsersViewModel by viewModels()
            usersModel.saveUserAsFavorite(this.selectedUser!!.asDatabaseModel())
        })

        if(intent.extras != null) {

            selectedUser = intent.getParcelableExtra<ApiUser>("selected_user")
            if(selectedUser != null) {

                Glide.with(this)
                    .asBitmap()
                    .load(selectedUser!!.picture.large)
                    .into(userPicture)

                val fullName = String.format("%s%s%s", selectedUser!!.name.first, " ",
                    selectedUser!!.name.last)

                name.text = fullName
                userName.text = selectedUser!!.login.username
                email.text = selectedUser!!.email
                val fullAddress = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                    selectedUser!!.location.street.number, " ",
                    selectedUser!!.location.street.name, "\n",
                    selectedUser!!.location.city, ", ",
                    selectedUser!!.location.state, " ",
                    selectedUser!!.location.postcode, "\n",
                    selectedUser!!.location.country)

                address.text = fullAddress
                phoneNumber.text = selectedUser!!.phone
                cellNumber.text = selectedUser!!.cell

            } else {
                addFavorite.visibility = View.GONE
                val favoriteUser = intent.getParcelableExtra<User>("favorite_user")
                Glide.with(this)
                    .asBitmap()
                    .load(favoriteUser.large)
                    .into(userPicture)

                name.text = favoriteUser.fullName
                userName.text = favoriteUser.username
                email.text = favoriteUser.email
                address.text = favoriteUser.address
                phoneNumber.text = favoriteUser.phoneNumber
                cellNumber.text = favoriteUser.cellNumber
            }
        }
    }
}