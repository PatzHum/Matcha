package com.patzhum.matcha

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
<<<<<<< HEAD
import android.util.MalformedJsonException
import android.view.View
=======
import android.view.View
import android.view.ViewGroup
>>>>>>> This fixes a UI issue where new Activities were being created.
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.patzhum.matcha.render.MatchaEditText
import com.patzhum.matcha.render.MatchaTextView
import com.patzhum.matcha.render.RenderUtil

class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var rootLayout : LinearLayout
    private lateinit var dbRootRef : DatabaseReference
    private var rootViewId : Int? = null

    val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        checkAuth()
        super.onCreate(savedInstanceState)

        dbRootRef = FirebaseDatabase.getInstance().getReference()

        addActiveEditorContentHook()

        render(null)
    }

    fun addActiveEditorContentHook() {
        dbRootRef.child("users/" + auth.currentUser?.uid + "/activeEditorContent").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                Log.e(LOG_TAG, error.toString());
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                render(snapshot?.getValue(String::class.java))
            }

        })
    }
    fun renderJsonErrorMessage() {
        setContentView(R.layout.render_error)
        val errorView = findViewById<TextView>(R.id.error_message)
        errorView.text = getString(R.string.json_render_error_message)
    }
    fun render(nullableJson : String?) {
        val json = nullableJson ?: "{}"
        setContentView(LinearLayout(this))
        rootLayout = LinearLayout(this)

<<<<<<< HEAD
        var view : View? = null
        try {
             view = when (RenderUtil.getType(json)) {
                "TextView" -> RenderUtil.renderView(this, MatchaTextView::class.java, json)
                "EditText" -> RenderUtil.renderView(this, MatchaEditText::class.java, json)
                else -> null
            }
        } catch (e : MalformedJsonException) {
            renderJsonErrorMessage()
            return
        } catch (e : JsonSyntaxException) {
            renderJsonErrorMessage()
            return
        }
=======
        var viewRoot: ViewGroup? = null
        rootViewId?.let {
            val contentView = findViewById<ViewGroup>(it)
            viewRoot = contentView?.parent as ViewGroup
            viewRoot?.removeView(contentView)
        }
        rootLayout = LinearLayout(this)
        rootLayout.id = View.generateViewId()
        rootViewId = rootLayout.id

        val gson = Gson()
>>>>>>> This fixes a UI issue where new Activities were being created.

        if (view != null) {
            rootLayout.addView(view)
        }

<<<<<<< HEAD
        setContentView(rootLayout)
=======
        viewRoot?.addView(rootLayout) ?: setContentView(rootLayout)

>>>>>>> This fixes a UI issue where new Activities were being created.
    }

    fun checkAuth() {
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
