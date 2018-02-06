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

public class APIFriends extends APIRequester {

    private final String route = baseURL + "/friends/";
    private static List<Friend> friendsCache;
    private static List<Invitation> invitationsCache;

    public void add(final Context context, final String requesterEmail, final String receiverEmail)
            throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "add-friend", content, Request.Method.POST, context,
                    new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Ami ajouté", Toast.LENGTH_SHORT).show();
                        }
                    } catch(JSONException jsonEx){
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, "Erreur : Impossible d'ajouter cet ami.",
                            Toast.LENGTH_SHORT).show();

                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception addEx){
            Log.e("add() Friend", addEx.getMessage());
        }
    }

    public void delete(final Context context, final String requesterEmail,
                       final String receiverEmail, final boolean isFromInvitations) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "delete-friend", content, Request.Method.POST,
                    context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Ami supprimé", Toast.LENGTH_SHORT).show();

                            if(isFromInvitations){
                                ((Invitations)context).fillInvitationsList();
                            } else {
                                ((Friends)context).fillFriendsList();
                            }
                        }
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erreur : Impossible de supprimer cet ami",
                            Toast.LENGTH_SHORT).show();

                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception deleteEx){
            Log.e("delete() Friend", deleteEx.getMessage());
        }
    }

    public void confirmInvitation(final Context context, final String receiverEmail)
            throws JSONException {
        JSONObject content = new JSONObject();

        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "accept-invitation", content, Request.Method.POST,
                    context, new APICallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            try {
                                if(result.getBoolean("result")){
                                    Toast.makeText(context, "Requête d'ami acceptée",
                                            Toast.LENGTH_SHORT).show();

                                    ((Invitations)context).fillInvitationsList();
                                }
                            } catch (JSONException jsonEx) {
                                Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,
                                    "Erreur : Impossible de traiter la requête d'ami.",
                                    Toast.LENGTH_SHORT).show();

                            if(error.networkResponse == null) return;

                            String errorData = new String(error.networkResponse.data);
                            Log.e("response", errorData);
                        }
                    });
        } catch (Exception confirmEx){
            Log.e("confirmInvitation() Friend", confirmEx.getMessage());
        }
    }

    public void denyInvitation(final Context context, final String receiverEmail)
            throws JSONException {
        JSONObject content = new JSONObject();

        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "reject-invitation", content, Request.Method.POST,
                    context, new APICallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            try {
                                if(result.getBoolean("result")){
                                    Toast.makeText(context, "Requête d'ami refusée",
                                            Toast.LENGTH_SHORT).show();

                                    ((Invitations)context).fillInvitationsList();
                                }
                            } catch (JSONException jsonEx) {
                                Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,
                                    "Erreur : Impossible de traiter la requête d'ami.",
                                    Toast.LENGTH_SHORT).show();

                            if(error.networkResponse == null) return;

                            String errorData = new String(error.networkResponse.data);
                            Log.e("response", errorData);
                        }
                    });
        } catch (Exception denyEx){
            Log.e("denyInvitation() Friend", denyEx.getMessage());
        }
    }

    public void getMyFriends(final Context context, final String requesterEmail)
            throws JSONException{
         if(friendsCache != null){
            TextView loadingText = ((Friends)context).findViewById(R.id.LoadText);
            loadingText.setVisibility(View.INVISIBLE);

            FriendAdapter friendsListAdapter = new FriendAdapter(context, friendsCache);
            ((Friends)context).setListAdapter(friendsListAdapter);
        }

        JSONObject content = new JSONObject();
        content.put("emailAddress", requesterEmail);

        try{
            this.readFromUrl(this.route + "get-my-friends", content, Request.Method.GET,
                    context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")) {
                            List<Friend> friendsList = new ArrayList<>();
                            JSONArray friendsArray = result.getJSONArray("friends");

                            for (int i = 0; i < friendsArray.length(); i++) {
                                JSONObject friendObject = friendsArray.getJSONObject(i);

                                JSONObject friendData = friendObject.getJSONObject("friendData");

                                String friendEmail = friendData.getString("email");
                                String friendLastname = friendData.getString("lastname");
                                String friendFirstname = friendData.getString("firstname");

                                Friend friend = new Friend(GlobalVariables.CurrentAccount,
                                        friendLastname, friendFirstname, friendEmail);

                                friendsList.add(friend);
                            }

                            friendsCache = friendsList;

                            FriendAdapter friendsListAdapter = new FriendAdapter(context,
                                    friendsList);

                            ((Friends)context).setListAdapter(friendsListAdapter);
                        }
                        TextView loadingText = ((Friends)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception getMyFriendsEx){
            Log.e("getMyFriends() Friend", getMyFriendsEx.getMessage());
        }
    }

    public void getMyInvitations(final Context context, final String requesterEmail)
            throws JSONException {
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
                    try {
                        if(result.getBoolean("result")) {

                            List<Invitation> invitationsList = new ArrayList<>();
                            JSONArray invitations = result.getJSONArray("invitations");

                            for (int i = 0; i < invitations.length(); i++) {
                                JSONObject invitation = invitations.getJSONObject(i);
                                int status = invitation.getInt("status");

                                JSONObject ownerData = invitation.getJSONObject("owner");
                                String senderEmail = ownerData.getString("email");
                                String senderLastname = ownerData.getString("lastname");
                                String senderFirstname = ownerData.getString("firstname");

                                JSONObject friendData = invitation.getJSONObject("friend");
                                String recipientEmail = friendData.getString("email");
                                String recipientLastname = friendData.getString("lastname");
                                String recipientFirstname = friendData.getString("firstname");

                                Invitation invitationObject =
                                        new Invitation(senderEmail, senderFirstname,
                                                senderLastname, recipientEmail,
                                                recipientFirstname, recipientLastname, status);

                                invitationsList.add(invitationObject);
                            }

                            invitationsCache = invitationsList;

                            InvitationAdapter invitationsListAdapter = new InvitationAdapter(context, invitationsList);
                            ((Invitations)context).setListAdapter(invitationsListAdapter);
                        }

                        TextView loadingText = ((Invitations)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessResponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception getMyInvitationsEx){
            Log.e("getMyInvitations() Invitation", getMyInvitationsEx.getMessage());
        }
    }
}
