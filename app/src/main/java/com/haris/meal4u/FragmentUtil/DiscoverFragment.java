package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.ActivityUtil.Base;
import com.haris.meal4u.AdapterUtil.CategorySpinnerAdapter;
import com.haris.meal4u.AdapterUtil.FilterAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.FilterObject;
import com.haris.meal4u.ObjectUtil.ViewTypeObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private String TAG = DiscoverFragment.class.getName();
    private ImageView imageFilter;
    private AppCompatSpinner spinnerMenu;
    private CategorySpinnerAdapter spinnerMenuAdapter;
    private ArrayList<ViewTypeObject> menuArraylist = new ArrayList<>();
    private ArrayList<DataObject> selectedCuisines = new ArrayList<>();
    private FilterObject filterObject;
    private String fragmentType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_discover, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        imageFilter = view.findViewById(R.id.image_filter);
        imageFilter.setVisibility(View.VISIBLE);
        imageFilter.setImageResource(R.drawable.ic_filter);

        menuArraylist.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.select_screen_type)));
        menuArraylist.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.discover_view)));
        menuArraylist.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.map_view)));

        spinnerMenu = (AppCompatSpinner) view.findViewById(R.id.spinner_screen_type);
        spinnerMenuAdapter = new CategorySpinnerAdapter(getActivity(), menuArraylist, true);
        spinnerMenu.setAdapter(spinnerMenuAdapter);

        spinnerMenu.setSelection(1);


        imageFilter.setOnClickListener(this);
        spinnerMenu.setOnItemSelectedListener(this);

    }


    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_fragment, fragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void onClick(View v) {
        if (v == imageFilter) {
            showFilterDialog(getActivity(), ((Base) getActivity()).objectArrayList);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            return;

        Fragment requireFragment = null;

        if (menuArraylist.get(position).getTitle()
                .equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.discover_view))) {

            requireFragment = new ListviewFragment();
            fragmentType = Utility.getStringFromRes(getActivity(), R.string.discover_view);

        } else if (menuArraylist.get(position).getTitle()
                .equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.map_view))) {

            requireFragment = new MapviewFragment();
            fragmentType = Utility.getStringFromRes(getActivity(), R.string.map_view);

        }

        if (filterObject != null) {

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.IntentKey.FILTER, filterObject);
            requireFragment.setArguments(bundle);

        }

        openFragment(requireFragment);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                txtRadius.setText(seekParams.progress + " " + Utility.getStringFromRes(getActivity(), R.string.radius));
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

                    Fragment requireFragment = null;

                    if (fragmentType.equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.discover_view))) {

                        requireFragment = new ListviewFragment();
                        fragmentType = Utility.getStringFromRes(getActivity(), R.string.discover_view);

                    } else if (fragmentType.equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.map_view))) {

                        requireFragment = new MapviewFragment();
                        fragmentType = Utility.getStringFromRes(getActivity(), R.string.map_view);

                    }

                    openFragment(requireFragment);

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

                    Fragment requireFragment = null;

                    if (fragmentType.equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.discover_view))) {

                        requireFragment = new ListviewFragment();
                        fragmentType = Utility.getStringFromRes(getActivity(), R.string.discover_view);

                    } else if (fragmentType.equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.map_view))) {

                        requireFragment = new MapviewFragment();
                        fragmentType = Utility.getStringFromRes(getActivity(), R.string.map_view);

                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.IntentKey.FILTER, filterObject);
                    requireFragment.setArguments(bundle);
                    openFragment(requireFragment);

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
            c01.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c01.setTag(0);
        }

        if (selectedCard != c02) {
            c02.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c02.setTag(0);
        }

        if (selectedCard != c03) {
            c03.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c03.setTag(0);
        }

        if (selectedCard != c04) {
            c04.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c04.setTag(0);
        }


        if (selectedCard != c05) {
            c05.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
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
            c01.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c01.setTag(0);
        }

        if (selectedCard != c02) {
            c02.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c02.setTag(0);
        }

        if (selectedCard != c03) {
            c03.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
            c03.setTag(0);
        }

        if (selectedCard != c04) {
            c04.setCardBackgroundColor(Utility.getAttrColor(getActivity(), R.attr.colorBackground));
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


}

