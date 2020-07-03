package com.haris.meal4u.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.AdapterUtil.FilterAdapter;
import com.haris.meal4u.AdapterUtil.SearchAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.NearbyCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.FilterObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.NativeAdObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity implements View.OnClickListener, ConnectionCallback,
        NearbyCallback, TextView.OnEditorActionListener, TextWatcher {
    private String TAG = Search.class.getName();
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewSearch;
    private SearchAdapter searchAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<DataObject> selectedCuisines = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private boolean loading = true;
    private StringBuilder stringBuilder = new StringBuilder();
    private PrefObject prefObject;
    private FilterObject filterObject;
    private String radius;
    private String rating;
    private String expense;
    private String category;
    private EditText editSearch;
    private ImageView imageFilter;
    private ImageView imageClose;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI(); //Initialize UI



    }


    /**
     * <p>It initialize the UI</p>
     */

    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        editSearch = findViewById(R.id.edit_search);

        /*imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);*/

        imageFilter = findViewById(R.id.image_filter);
        imageClose = findViewById(R.id.image_close);


        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.start_searching))
                .setDescription(Utility.getStringFromRes(this, R.string.searching_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_search));

        //Initialize Layout Manager & setup with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof DataObject) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerViewSearch = (RecyclerView) findViewById(R.id.recycler_view_search);
        recyclerViewSearch.setLayoutManager(gridLayoutManager);

        //Setup Recycler View Adapter with Adapter

        searchAdapter = new SearchAdapter(this, objectArrayList, this) {
            @Override
            public void selectPlace(int position) {

            }
        };
        recyclerViewSearch.setAdapter(searchAdapter);
        recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Utility.Logger(TAG, "Last Item Wow !");
                            objectArrayList.add(new ProgressObject().setScrollLoading(true));
                            management.sendRequestToServer(new RequestObject()
                                    .setFirstRequest(false)
                                    .setJson(getJson(String.valueOf(Constant.getBaseLatLng().latitude), String.valueOf(Constant.getBaseLatLng().longitude),
                                            radius, editSearch.getText().toString(), stringBuilder.toString(), category, rating, expense))
                                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                    .setConnection(Constant.CONNECTION.SEARCH)
                                    .setConnectionCallback(Search.this));
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        //imageBack.setOnClickListener(this);
        imageFilter.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        editSearch.setOnEditorActionListener(this);
        editSearch.addTextChangedListener(this);

    }




    /**
     * <p>It trigger by Search & find required search terms</p>
     */

    private void performSearch(String searchText) {

        Constant.CONNECTION connection = null;
        Utility.Logger(TAG, "Search : " + searchText);


        //For showing Progressing Animation

        Utility.hideKeyboard(this, editSearch);

        //Send Request to Server regarding specific Category

        if (filterObject != null) {

            radius = Utility.isEmptyString(filterObject.getRadius()) ? Constant.AppConfiguration.DEFAULT_RADIUS :
                    filterObject.getRadius();

            rating = Utility.isEmptyString(filterObject.getRating()) ? null :
                    filterObject.getRating();

            expense = Utility.isEmptyString(filterObject.getExpense()) ? null :
                    filterObject.getExpense();

            category = Utility.isEmptyString(filterObject.getCusines()) ? null :
                    filterObject.getCusines();

        } else {

            radius = Constant.AppConfiguration.DEFAULT_RADIUS;
            rating = null;
            expense = null;
            category = null;

        }

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(String.valueOf(Constant.getBaseLatLng().latitude), String.valueOf(Constant.getBaseLatLng().longitude),
                        radius, searchText, stringBuilder.toString(), category, rating, expense))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.SEARCH)
                .setConnectionCallback(this));

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        searchAdapter.notifyDataSetChanged();


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String latitude, String longitude, String radius, String searchTerm, String skipIds, String category, String rating, String expense) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "search_restaurant");
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);
            jsonObject.accumulate("radius", radius);
            jsonObject.accumulate("search_term", searchTerm);

            if (!Utility.isEmptyString(category))
                jsonObject.accumulate("category", category);

            if (!Utility.isEmptyString(skipIds))
                jsonObject.accumulate("skip_ids", skipIds);

            if (!Utility.isEmptyString(rating))
                jsonObject.accumulate("rating", rating);

            if (!Utility.isEmptyString(expense))
                jsonObject.accumulate("expense", expense);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v == imageFilter) {
            showFilterDialog(this, Base.objectArrayList);
        }
        if (v == imageClose) {
            editSearch.setText(null);
            imageClose.setVisibility(View.GONE);
            objectArrayList.clear();
            searchAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {


            if (requestObject.isFirstRequest()) {
                objectArrayList.clear();
            } else {
                stringBuilder.append(",");
                int listLastItem = objectArrayList.size() - 1;
                objectArrayList.remove(listLastItem);

            }

            loading = true;
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                if (i != 0 && 0 == i % Constant.Credentials.nativeAdInterval && Constant.Credentials.isFbNativeAds)
                    objectArrayList.add(new NativeAdObject());

                if (favouriteMap.containsKey(dataObject.getObjectArrayList().get(i).getObject_id()))
                    dataObject.getObjectArrayList().get(i).setFavourite(true);

                objectArrayList.add(dataObject.getObjectArrayList().get(i));

                stringBuilder.append("'");
                stringBuilder.append(dataObject.getObjectArrayList().get(i).getObject_id());
                stringBuilder.append("'");

                if (i < (dataObject.getObjectArrayList().size() - 1))
                    stringBuilder.append(",");

            }


            searchAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(this, R.string.no_search_result))
                    .setDescription(Utility.getStringFromRes(this, R.string.no_search_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_search));

        } else {
            int listLastItem = objectArrayList.size() - 1;
            objectArrayList.remove(listLastItem);
        }

        loading = false;
        searchAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSelect(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);
        Intent intent = new Intent(this, RestaurantDetail.class);
        intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
        startActivity(intent);
    }

    @Override
    public void onFavourite(int position, boolean isFavourite) {
        if (!prefObject.isLogin()) {
            startActivity(new Intent(this, OnBoarding.class));
            return;
        }


        DataObject dtObject = (DataObject) objectArrayList.get(position);
        ((DataObject) objectArrayList.get(position)).setFavourite(isFavourite);
        searchAdapter.notifyItemChanged(position);

        if (isFavourite) {

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.INSERT)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())
                            .setObject_id(dtObject.getObject_id())));


        } else {

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.DELETE_SPECIFIC_TYPE)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())
                            .setObject_id(dtObject.getObject_id())));

        }
    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showFilterDialog(final Context context, ArrayList<Object> dataList) {

        final CardView cardOne;
        final CardView cardTwo;
        final CardView cardThree;
        final CardView cardFour;
        final CardView cardFive;

        final CardView cardExpenseOne;
        final CardView cardExpenseTwo;
        final CardView cardExpenseThree;
        final CardView cardExpenseFour;

        CardView cardSubmit;
        CardView cardClose;

        final TextView txtRadius;

        FilterAdapter filterAdapter = null;
        final String[] cuisines = {""};

        final View view = getLayoutInflater().inflate(R.layout.filter_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        cardClose = view.findViewById(R.id.card_cancel);
        cardSubmit = view.findViewById(R.id.card_filter);

        txtRadius = view.findViewById(R.id.txt_radius);


        //region Manage Recycler View with Adapter

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        final RecyclerView recyclerViewCuisine = view.findViewById(R.id.recycler_cuisines);
        recyclerViewCuisine.setLayoutManager(gridLayoutManager);

        final ArrayList<Object> itemArraylist = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {

            DataObject item = (DataObject) dataList.get(i);

            if (filterObject != null) {

                if (item.getCategory_name().equalsIgnoreCase(filterObject.getCusines()))
                    item.setLongTap(true);

            } else {
                item.setLongTap(false);
            }

            itemArraylist.add(item);

        }

        filterAdapter = new FilterAdapter(context, itemArraylist) {
            @Override
            public void selectItem(int position) {


            }
        };
        recyclerViewCuisine.setAdapter(filterAdapter);

        //endregion

        cardOne = (CardView) view.findViewById(R.id.card_one);
        cardTwo = (CardView) view.findViewById(R.id.card_two);
        cardThree = (CardView) view.findViewById(R.id.card_three);
        cardFour = (CardView) view.findViewById(R.id.card_four);
        cardFive = (CardView) view.findViewById(R.id.card_five);

        //region Manage Card Selection

        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardOne.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardOne.setTag(1);
                manageRatingCardsSelection(view, cardOne);

            }
        });

        cardTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardTwo.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardTwo.setTag(1);
                manageRatingCardsSelection(view, cardTwo);

            }
        });

        cardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardThree.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardThree.setTag(1);
                manageRatingCardsSelection(view, cardThree);

            }
        });

        cardFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardFour.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardFour.setTag(1);
                manageRatingCardsSelection(view, cardFour);

            }
        });

        cardFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardFive.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardFive.setTag(1);
                manageRatingCardsSelection(view, cardFive);

            }
        });

        //endregion


        cardExpenseOne = (CardView) view.findViewById(R.id.card_expense_one);
        cardExpenseTwo = (CardView) view.findViewById(R.id.card_expense_two);
        cardExpenseThree = (CardView) view.findViewById(R.id.card_expense_three);
        cardExpenseFour = (CardView) view.findViewById(R.id.card_expense_four);

        //region Manage Expense Selection

        cardExpenseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardExpenseOne.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardExpenseOne.setTag(1);
                manageExpenseCardsSelection(view, cardExpenseOne);

            }
        });

        cardExpenseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardExpenseTwo.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardExpenseTwo.setTag(1);
                manageExpenseCardsSelection(view, cardExpenseTwo);

            }
        });

        cardExpenseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardExpenseThree.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardExpenseThree.setTag(1);
                manageExpenseCardsSelection(view, cardExpenseThree);

            }
        });

        cardExpenseFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardExpenseFour.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
                cardExpenseFour.setTag(1);
                manageExpenseCardsSelection(view, cardExpenseFour);

            }
        });


        //endregion


        final IndicatorSeekBar seekBarDuration = view.findViewById(R.id.seekbar_radius);
        seekBarDuration.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                txtRadius.setText(seekParams.progress + " " + Utility.getStringFromRes(getApplicationContext(), R.string.radius));
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });


        if (filterObject == null)
            cardClose.setVisibility(View.GONE);

        if (filterObject != null) {

            if (filterObject.getRadius() != null)
                seekBarDuration.setProgress(Integer.parseInt(filterObject.getRadius()));

            //region Process Rating Fields

            if (filterObject.getRating() != null &&
                    filterObject.getRating().equalsIgnoreCase("1.0")) {

                cardOne.performClick();

            } else if (filterObject.getRating() != null &&
                    filterObject.getRating().equalsIgnoreCase("2.0")) {

                cardTwo.performClick();

            } else if (filterObject.getRating() != null &&
                    filterObject.getRating().equalsIgnoreCase("3.0")) {

                cardThree.performClick();

            } else if (filterObject.getRating() != null &&
                    filterObject.getRating().equalsIgnoreCase("4.0")) {

                cardFour.performClick();

            } else if (filterObject.getRating() != null &&
                    filterObject.getRating().equalsIgnoreCase("5.0")) {

                cardFive.performClick();

            }

            //endregion

            //region Process Expense fields
            if (filterObject.getExpense() != null &&
                    filterObject.getExpense().equalsIgnoreCase("1")) {

                cardExpenseOne.performClick();

            } else if (filterObject.getExpense() != null &&
                    filterObject.getExpense().equalsIgnoreCase("2")) {

                cardExpenseTwo.performClick();

            } else if (filterObject.getExpense() != null &&
                    filterObject.getExpense().equalsIgnoreCase("3")) {

                cardExpenseThree.performClick();

            } else if (filterObject.getExpense() != null &&
                    filterObject.getExpense().equalsIgnoreCase("4")) {

                cardExpenseFour.performClick();

            }

            //endregion

        }


        cardClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    filterObject = null;
                    bottomSheetDialog.dismiss();
                }
            }
        });

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    selectedCuisines.clear();

                    for (int i = 0; i < itemArraylist.size(); i++) {

                        DataObject dtObject = (DataObject) itemArraylist.get(i);

                        if (dtObject.isLongTap()) {
                            selectedCuisines.add(dtObject);
                            if (Utility.isEmptyString(cuisines[0]))
                                cuisines[0] += dtObject.getCategory_id();
                            else
                                cuisines[0] += "," + dtObject.getCategory_id();
                        }

                    }

                    String progress = seekBarDuration.getProgress() == 0 ? null :
                            String.valueOf(seekBarDuration.getProgress());

                    filterObject = new FilterObject()
                            .setRadius(progress)
                            .setExpense(getSelectedExpenseCard(view))
                            .setRating(getSelectedRatingCard(view))
                            .setCusines(cuisines[0])
                            .setDataObjects(selectedCuisines);

                    Utility.Logger(TAG, filterObject.toString());


                    bottomSheetDialog.dismiss();
                }
            }
        });


    }

    /**
     * <p>It is used to manage rating cards selection</p>
     *
     * @param selectedCard
     */
    private void manageRatingCardsSelection(View base, CardView selectedCard) {

        CardView c01 = base.findViewById(R.id.card_one);
        CardView c02 = base.findViewById(R.id.card_two);
        CardView c03 = base.findViewById(R.id.card_three);
        CardView c04 = base.findViewById(R.id.card_four);
        CardView c05 = base.findViewById(R.id.card_five);

        if (selectedCard != c01) {
            c01.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c01.setTag(0);
        }

        if (selectedCard != c02) {
            c02.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c02.setTag(0);
        }

        if (selectedCard != c03) {
            c03.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c03.setTag(0);
        }

        if (selectedCard != c04) {
            c04.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c04.setTag(0);
        }


        if (selectedCard != c05) {
            c05.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c05.setTag(0);
        }
    }


    /**
     * <p>It is used to manage expense cards selection</p>
     *
     * @param selectedCard
     */
    private void manageExpenseCardsSelection(View base, CardView selectedCard) {

        CardView c01 = base.findViewById(R.id.card_expense_one);
        CardView c02 = base.findViewById(R.id.card_expense_two);
        CardView c03 = base.findViewById(R.id.card_expense_three);
        CardView c04 = base.findViewById(R.id.card_expense_four);

        if (selectedCard != c01) {
            c01.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c01.setTag(0);
        }

        if (selectedCard != c02) {
            c02.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c02.setTag(0);
        }

        if (selectedCard != c03) {
            c03.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c03.setTag(0);
        }

        if (selectedCard != c04) {
            c04.setCardBackgroundColor(Utility.getAttrColor(this, R.attr.colorBackground));
            c04.setTag(0);
        }

    }


    /**
     * <p>It is used to get Selected Rating Card</p>
     *
     * @param base
     */
    private String getSelectedRatingCard(View base) {

        String selection = null;

        CardView c01 = base.findViewById(R.id.card_one);
        CardView c02 = base.findViewById(R.id.card_two);
        CardView c03 = base.findViewById(R.id.card_three);
        CardView c04 = base.findViewById(R.id.card_four);
        CardView c05 = base.findViewById(R.id.card_five);

        if (c01.getTag() != null && (int) c01.getTag() == 1)
            selection = "1.0";

        if (c02.getTag() != null && (int) c02.getTag() == 1)
            selection = "2.0";

        if (c03.getTag() != null && (int) c03.getTag() == 1)
            selection = "3.0";

        if (c04.getTag() != null && (int) c04.getTag() == 1)
            selection = "4.0";

        if (c05.getTag() != null && (int) c05.getTag() == 1)
            selection = "5.0";

        return selection;

    }


    /**
     * <p>It is used to get Selected Expense Card</p>
     *
     * @param base
     */
    private String getSelectedExpenseCard(View base) {

        String selection = null;

        CardView c01 = base.findViewById(R.id.card_expense_one);
        CardView c02 = base.findViewById(R.id.card_expense_two);
        CardView c03 = base.findViewById(R.id.card_expense_three);
        CardView c04 = base.findViewById(R.id.card_expense_four);

        if (c01.getTag() != null && (int) c01.getTag() == 1)
            selection = "1";

        if (c02.getTag() != null && (int) c02.getTag() == 1)
            selection = "2";

        if (c03.getTag() != null && (int) c03.getTag() == 1)
            selection = "3";

        if (c04.getTag() != null && (int) c04.getTag() == 1)
            selection = "4";


        return selection;

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utility.isEmptyString(s.toString())) {
            imageClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onResume() {
        super.onResume();

        if (!prefObject.isLogin())
            return;

        favouriteArraylist.clear();
        favouriteArraylist.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.SPECIFIC_ID)
                .setDataObject(new DataObject()
                        .setUser_id(prefObject.getUserId()))));

        favouriteMap.clear();

        for (int i = 0; i < favouriteArraylist.size(); i++) {
            DataObject favouriteObject = (DataObject) favouriteArraylist.get(i);
            favouriteMap.put(favouriteObject.getObject_id(), favouriteObject.getUser_id());
        }


    }


}

