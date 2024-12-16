package edu.uncc.posts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Objects;

import edu.uncc.posts.databinding.FragmentPostsBinding;
import edu.uncc.posts.databinding.PostRowItemBinding;
import edu.uncc.posts.models.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Assignment 16
//PostsFragment.java
//Ethan Fowler Raziuddin Syed Khaja

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();

    SharedPreferences userInfo;
    OkHttpClient client = new OkHttpClient();
    int pageNumber = 1;
    int totalPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        userInfo = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        binding.textViewTitle.setText("Welcome " + userInfo.getString("userName", ""));

        getPosts(userInfo);



        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.logout();
            }
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter();
        binding.recyclerViewPosts.setAdapter(postsAdapter);

        binding.textViewPaging.setText("Loading ...");

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Sadia help with this part
                pageNumber--;
                if(pageNumber == 0)
                {
                    pageNumber = 1;
                }
                getPosts(userInfo);
            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Sadia helped with this part
                if(pageNumber <= (totalPosts / 10))
                {
                    pageNumber++;
                }
                getPosts(userInfo);
            }
        });

        getActivity().setTitle(R.string.posts_label);
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder>
    {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position)
        {
            Post post = mPosts.get(position);

            holder.setupUI(post);
        }

        @Override
        public int getItemCount()
        {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder
        {
            PostRowItemBinding mBinding;
            Post mPost;
            public PostsViewHolder(PostRowItemBinding binding)
            {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post)
            {
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());

                if(!userInfo.getString("userId", "").equals(post.getCreated_by_uid()))
                {
                    mBinding.imageViewDelete.setVisibility(View.GONE);
                }
                else
                {
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            AlertDialog.Builder choice = new AlertDialog.Builder(getActivity());
                            choice.setTitle("Warning")
                                    .setMessage("Are you sure you want to delete this post?")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            deletePost(userInfo, post);
                                            postsAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {

                                        }
                                    });
                            choice.create().show();
                        }
                    });
                }



            }
        }

    }

    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
    }

    void getPosts(SharedPreferences info)
    {

        String token = info.getString("token", "");

        HttpUrl.Builder builder = new HttpUrl.Builder();

        HttpUrl url = builder.scheme("https").host("www.theappsdr.com").addPathSegment("posts").addQueryParameter("page", String.valueOf(pageNumber)).build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + token)
                .build();



        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    String body = response.body().string();

                    try
                    {
                        JSONObject json = new JSONObject(body);
                        JSONArray postsJsonArray = json.getJSONArray("posts");
                        totalPosts = json.getInt("totalCount");
                        mPosts.clear();
                        for(int i = 0; i < postsJsonArray.length(); i++)
                        {
                            try
                            {
                                JSONObject postsJsonObject = postsJsonArray.getJSONObject(i);
                                Post post = new Post();
                                post.setCreated_at(postsJsonObject.getString("created_at"));
                                post.setCreated_by_name(postsJsonObject.getString("created_by_name"));
                                post.setCreated_by_uid(postsJsonObject.getString("created_by_uid"));
                                post.setPost_id(postsJsonObject.getString("post_id"));
                                post.setPost_text(postsJsonObject.getString("post_text"));

                                mPosts.add(post);

                            }
                            catch(JSONException e)
                            {

                            }
                        }
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {

                                postsAdapter.notifyDataSetChanged();
                                //Sean helped with the page number
                                binding.textViewPaging.setText("Showing Page " + String.valueOf(pageNumber) + " out of " + (int) (totalPosts / 10));
                            }
                        });

                    }
                    catch(JSONException e)
                    {

                    }
                }
            }
        });
    }

    void deletePost(SharedPreferences info, Post post)
    {
        String token = info.getString("token", "");

        FormBody formBody = new FormBody.Builder()
                .add("post_id", post.getPost_id())
                .build();

        //Sadia helped with the post formBody part
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/delete")
                .post(formBody)
                .addHeader("Authorization", "BEARER " + token)
                .build();


        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try
                    {
                        JSONObject json = new JSONObject(body);

                        String status = json.getString("status");



                        if(status.equals("ok"))
                        {
                            mPosts.remove(post);
                            getPosts(info);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Sadia caught my error of not notifying the adapter on the ui thread
                                    postsAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                        if(!status.equals("error"))
                        {
                            String message = json.getString("message");
                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    //Sadia caught my error of not putting the toast in the run on ui thread
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                    catch(JSONException e)
                    {

                    }
                }
            }
        });
    }
}