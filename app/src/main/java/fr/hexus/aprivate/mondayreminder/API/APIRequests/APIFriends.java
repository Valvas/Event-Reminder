package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.FriendAdapter;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.InvitationAdapter;
import fr.hexus.aprivate.mondayreminder.Activities.Friends;
import fr.hexus.aprivate.mondayreminder.Activities.Invitations;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Contracts.Friend;
import fr.hexus.aprivate.mondayreminder.Contracts.Invitation;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIFriends extends APIRequester {

    private final String route = baseURL + "/friends/";
    private static List<Friend> friendsCache;
    private static List<Invitation> invitationsCache;

    public void Add(final Context context, final String requesterEmail, final String receiverEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "add-friend", content, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Friend added!", Toast.LENGTH_SHORT).show();
                        }
                    } catch(JSONException jsonEx){
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, "Error: could not add friend.\nPlease check your friend's email address." + error.getMessage(), Toast.LENGTH_SHORT).show();

                    if(error.networkResponse == null) return;

                    String test = new String(error.networkResponse.data);
                    Log.i("response", test);
                }
            });
        } catch (Exception addEx){
            Log.e("Add() Friend", addEx.getMessage());
        }
    }

    public void Delete(final Context context, final String requesterEmail, final String receiverEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "delete-friend", content, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    Toast.makeText(context, "Friend deleted!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, "Error: could not delete friend.\nPlease check your friend's email address.", Toast.LENGTH_SHORT).show();

                    if(error.networkResponse == null) return;

                    String test = new String(error.networkResponse.data);
                    Log.i("response", test);
                }
            });
        } catch (Exception deleteEx){
            Log.e("Delete() Friend", deleteEx.getMessage());
        }
    }

    public void Update(final Context context, final String requesterEmail, final String receiverEmail, final String status) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);
        content.put("status", status);

        try{
            this.readFromUrl(this.route + "update-friend-status", content, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            // Handle response from server
                            Toast.makeText(context, "Friend request accepted!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, "Error: could not accept friend request.", Toast.LENGTH_SHORT).show();

                    if(error.networkResponse == null) return;

                    String test = new String(error.networkResponse.data);
                    Log.i("response", test);
                }
            });
        } catch (Exception updateEx){
            Log.e("Update() Friend", updateEx.getMessage());
        }
    }

    public void getMyFriends(final Context context, final String requesterEmail) throws JSONException{
         if(friendsCache != null){
            TextView loadingText = ((Friends)context).findViewById(R.id.LoadText);
            loadingText.setVisibility(View.INVISIBLE);

            FriendAdapter friendsListAdapter = new FriendAdapter(context, friendsCache);
            ((Friends)context).setListAdapter(friendsListAdapter);
        }

        JSONObject content = new JSONObject();
        content.put("emailAddress", requesterEmail);

        try{
            this.readFromUrl(this.route + "get-my-friends", content, Request.Method.GET, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")) {
                            List<Friend> friendsList = new ArrayList<>();
                            JSONObject friends;
                            friends = result.getJSONObject("friends");
                            Iterator<?> keys = friends.keys();

                            while (keys.hasNext()) {
                                JSONObject jsonFriend = friends.getJSONObject((String) keys.next());

                                String friendEmail = jsonFriend.getString("email");
                                String friendLastname = jsonFriend.getString("lastname");
                                String friendFirstname = jsonFriend.getString("firstname");

                                Friend friend = new Friend(GlobalVariables.CurrentAccount,
                                        friendLastname, friendFirstname, friendEmail);
                                friendsList.add(friend);
                            }

                            friendsCache = friendsList;

                            FriendAdapter friendsListAdapter = new FriendAdapter(context, friendsList);
                            ((Friends)context).setListAdapter(friendsListAdapter);
                        }
                        TextView loadingText = ((Friends)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        Log.e("onSuccessResponse:Failure", e.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    if(error.networkResponse == null) return;

                    String test = new String(error.networkResponse.data);
                    Log.i("response", test);
                }
            });
        } catch (Exception getFriendsEx){
            Log.e("getMyFriends() Friend", getFriendsEx.getMessage());
        }
    }

    public void getMyInvitations(final Context context, final String requesterEmail) throws JSONException {
        if(invitationsCache != null){
            TextView loadingText = ((Invitations)context).findViewById(R.id.LoadText);
            loadingText.setVisibility(View.INVISIBLE);

            InvitationAdapter invitationsLiteAdapter = new InvitationAdapter(context, invitationsCache);
            ((Invitations)context).setListAdapter(invitationsLiteAdapter);
        }

        JSONObject content = new JSONObject();
        content.put("emailAddress", requesterEmail);

        try{
            this.readFromUrl(this.route + "get-my-invitation-list", content, Request.Method.GET, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")) {

                            List<Invitation> invitationsList = new ArrayList<>();
                            JSONArray invitations;
                            invitations = result.getJSONArray("invitations");
                            for (int i = 0; i < invitations.length(); i++) {
                                JSONObject jsonInvitations = invitations.getJSONObject(i);

                                String recipientEmail = jsonInvitations.getString("friend_email");
                                String senderEmail = jsonInvitations.getString("owner_email");
                                int status = jsonInvitations.getInt("status");

                                Invitation invitation;

                                invitation = new Invitation(senderEmail, "SenderFirstname", "SenderLastname",
                                        recipientEmail, "RecipientFirstname", "RecipientLastname", status);

                                invitationsList.add(invitation);
                            }

                            invitationsCache = invitationsList;

                            InvitationAdapter invitationsListAdapter = new InvitationAdapter(context, invitationsList);
                            ((Invitations)context).setListAdapter(invitationsListAdapter);
                        }

                        TextView loadingText = ((Invitations)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        Log.e("onSuccessResponse:Failure", e.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    if(error.networkResponse == null) return;

                    String test = new String(error.networkResponse.data);
                    Log.i("response", test);
                }
            });
        } catch (Exception getFriendsEx){
            Log.e("getMyInvitations() Invitation", getFriendsEx.getMessage());
        }
    }
}
