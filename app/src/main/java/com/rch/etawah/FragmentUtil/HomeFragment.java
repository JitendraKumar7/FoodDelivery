package com.rch.etawah.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rch.etawah.ActivityUtil.RestaurantDetail;
import com.rch.etawah.AdapterUtil.HomeAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.InterfaceUtil.HomeCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.HomeObject;
import com.rch.etawah.ObjectUtil.InternetObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ConnectionCallback, HomeCallback {

    private TextView editSearch;
    private Management management;
    private HomeAdapter homeAdapter;
    private String TAG = HomeFragment.class.getName();
    private ArrayList<Object> objectArrayList = new ArrayList<>();


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        editSearch = view.findViewById(R.id.edit_search);

        objectArrayList.add(new ProgressObject());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                }
                //
                else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                }
                //
                else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                }
                //
                else if (objectArrayList.get(position) instanceof DataObject) {
                    return 1;
                }
                //
                else if (objectArrayList.get(position) instanceof HomeObject) {
                    return 1;
                }
                //
                else {
                    return 1;
                }
            }
        });

        RecyclerView recyclerViewHome = view.findViewById(R.id.recycler_view_home);
        recyclerViewHome.setLayoutManager(gridLayoutManager);

        homeAdapter = new HomeAdapter(getActivity(), objectArrayList, getFragmentManager(), this) {
            @Override
            public void select(boolean isLocked, int position) {


            }
        };
        recyclerViewHome.setAdapter(homeAdapter);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "home");
            jsonObject.accumulate("latitude", "28.58256");
            jsonObject.accumulate("longitude", "77.84737");
            jsonObject.accumulate("radius", "25");

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(jsonObject.toString())
                    .setConnection(Constant.CONNECTION.HOME)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnectionCallback(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            if (data instanceof DataObject) {

                objectArrayList.clear();
                DataObject dataObject = (DataObject) data;

                objectArrayList.addAll(dataObject.getObjectArrayList());

                homeAdapter.notifyDataSetChanged();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (objectArrayList.size() > 0) {

                            Bundle bundle = new Bundle();
                            DataObject dataObject = (DataObject) objectArrayList.get(0);
                            bundle.putParcelable(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);

                            //DashboardFragment fragment = new DashboardFragment();
                            //fragment.setArguments(bundle);

                            //((Base) getActivity()).openFragment(fragment);

                            //onSelect(-1, 0);
                        }
                    }
                });

            }
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        if (!Utility.isEmptyString(data) && requestObject != null) {

            Utility.Logger(TAG, "Error = " + data);
            objectArrayList.clear();
            homeAdapter.notifyDataSetChanged();
           /* if (favouriteArraylist.size() > 0)
                objectArrayList.add(new HomeObject()
                        .setData_type(Constant.DATA_TYPE.HISTORY)
                        .setTitle(Utility.getStringFromRes(getActivity(), R.string.continue_reading))
                        .setDataObjectArrayList(favouriteArraylist));*/
            //homeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onSelect(int parentPosition, int childPosition) {

        Utility.Logger(TAG, "parentPosition " + parentPosition + " childPosition " + childPosition);

        HomeObject homeObject = null;
        DataObject dataObject = null;


        if (parentPosition >= 0) {

            homeObject = (HomeObject) objectArrayList.get(parentPosition);
            dataObject = homeObject.getDataObjectArrayList().get(childPosition);

            if (homeObject.getData_type() == Constant.DATA_TYPE.FEATURED) {

                Intent intent = new Intent(getActivity(), RestaurantDetail.class);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                startActivity(intent);

            } else if (homeObject.getData_type() == Constant.DATA_TYPE.TOP_BRANDS) {

                Intent intent = new Intent(getActivity(), RestaurantDetail.class);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                startActivity(intent);

            }

        } else {

            Utility.Logger(TAG, "Main Click Working");

            dataObject = (DataObject) objectArrayList.get(childPosition);
            Intent intent = new Intent(getActivity(), RestaurantDetail.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
            startActivity(intent);

        }

        Utility.Logger(TAG, "Data = " + dataObject.toString());

    }

    @Override
    public void onMore(Constant.DATA_TYPE dataType) {

    }

    @Override
    public void switchLayout(int position, boolean isSwitchLayout) {


    }

    @Override
    public void onFavourite(int position, boolean isFavourite) {


    }

}

