package com.example.androidapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lib.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * No, it is not wrong to make the AuthenticationOperations class a utility class. In fact, it is a good design pattern to separate
 * the authentication logic into a separate class. This will help you to reuse the authentication code in other parts of your
 * application, without having to write the same code again and again. Additionally, it will also help you to organize your code
 * better and make it more modular.
 *
 * By making the class a utility class, you can access its methods from other parts of your application without having to create
 * an instance of the class. This can be helpful if you want to use the authentication logic in multiple activities or fragments
 * of your application.
 *
 * */
public class AuthenticationOperations {
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();


    private AuthenticationOperations(){}


    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        AuthenticationOperations.mAuth = mAuth;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static void setUser(FirebaseUser user) {
        AuthenticationOperations.user = user;
    }

    private static FirebaseUser user;

    public static boolean isLoggedIn() {

        return AuthenticationOperations.getUser()!=null;
    }



    /**
     * I added the final keyword to the OnSignInCompleteListener parameter because it is being used inside an anonymous
     * inner class, which requires that the parameter be effectively final. This means that the value of the parameter
     * should not be changed after it is initialized, in order for it to be accessed inside the inner class.
     *
     * By making the parameter final, it ensures that the parameter value will not be changed, and it will be accessible
     * inside the inner class.
     * */


    public static void signIn(String email,String password, final PlainTaskCompleteListener listener) {


        AuthenticationOperations.getmAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("correctLogin","successful in detecting correct login");
                    setUser(getmAuth().getCurrentUser());
                    listener.onBackendComplete(true);



                } else {
                    Log.i("wrongLogin","successful in detecting wrong login");
                    String exceptionReason=(task.getException().getMessage() != null) ? task.getException().getMessage() : "Exception Not Found";
                    listener.onBackendComplete(false);
                    Log.i("signInExceptionReason",exceptionReason);
                }
            }
        });


    }

    public static void registerUser(final String telegramHandle,final String email, final String password,final PlainTaskCompleteListener listener){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setUser(getmAuth().getCurrentUser());
                            String uid=getCurrentUserUid();
                            if (uid==null){
                                Log.i("telegramHandle",telegramHandle);
                                Log.i("registerUser","uid is null");
                                listener.onBackendComplete(false);
                            }
                            else {

                                User user = new User(telegramHandle, uid);
                                DatabaseOperations.addUser(user, new PlainTaskCompleteListener() {
                                    @Override
                                    public void onBackendComplete(boolean success) {
                                        if (success) {
                                            listener.onBackendComplete(true);
                                        } else {
                                            listener.onBackendComplete(false);
                                            String exceptionReason = "cannot add user to Database";
                                            Log.i("registerUser", exceptionReason);
                                        }
                                    }
                                });
                            }


                        } else {
                            String exceptionReason=(task.getException().getMessage() != null) ? task.getException().getMessage() : "Exception Not Found";
                            listener.onBackendComplete(false);
                            Log.i("registerUser,email",email);
                            Log.i("registerUser",exceptionReason);
                        }
                    }
                });

    }


    public static String getCurrentUserUid(){
        if (AuthenticationOperations.getUser()==null){
            Log.i("getCurrentUserUid","user is null");
            return null;
        }
        else{
            try{
                Log.i("getCurrentUserUid",getUser().getUid());
                return getUser().getUid();

            }catch(NullPointerException e){
                Log.i("getCurrentUserUid","null pointer exception occurred");
                return null;
            }


        }
    }



    public static void logout(){
        mAuth.signOut();

    }

}
