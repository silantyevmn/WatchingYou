package com.yurentsy.watchingyou.ui.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;
import com.yurentsy.watchingyou.App;
import com.yurentsy.watchingyou.R;
import com.yurentsy.watchingyou.mvp.model.entity.Person;
import com.yurentsy.watchingyou.mvp.presenter.PersonPresenter;
import com.yurentsy.watchingyou.mvp.presenter.SettingPresenter;
import com.yurentsy.watchingyou.mvp.view.PersonView;
import com.yurentsy.watchingyou.mvp.view.SettingView;
import com.yurentsy.watchingyou.ui.common.BackButtonListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.terrakok.cicerone.Router;

public class PersonFragment extends MvpAppCompatFragment implements PersonView,BackButtonListener {
    private static final String KEY_PERSON="person";

    @Inject
    Router router;

    @InjectPresenter
    PersonPresenter presenter;

    public static PersonFragment getNewInstance(Person person) {
        PersonFragment fragment = new PersonFragment();
        // TODO: 28.10.2018 если все же что-то добавил то fragment.setArguments(bundle)
        Bundle b=new Bundle();
        b.putSerializable(KEY_PERSON,(Person) person);
        fragment.setArguments(b);
        return fragment;
    }

    @ProvidePresenter
    public PersonPresenter provideGeneralPresenter() {
        return new PersonPresenter(AndroidSchedulers.mainThread(), router, (Person) getArguments().getSerializable(KEY_PERSON));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getInstance().getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, view);

        Toolbar toolbar=view.findViewById(R.id.d_toolbar);
        toolbar.setTitle(R.string.person);
        toolbar.setNavigationOnClickListener(item->{
            onBackPressed();
        });
        return view;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    @Override
    public void init() {
        //init
    }

    @BindView(R.id.card_name)
    TextView tvName;
    @BindView(R.id.card_surname)
    TextView tvSurname;
    @BindView(R.id.card_adress)
    TextView tvAddress;
    @BindView(R.id.card_email)
    TextView tvEmail;
    @BindView(R.id.card_phone)
    TextView tvPhone;
    @BindView(R.id.card_position)
    TextView tvPosition;
    @BindView(R.id.photo)
    ImageView imagePhoto;


    @Override
    public void setCard(Person p) {
        tvName.setText(tvName.getText()+":"+p.getName());
        tvSurname.setText(tvSurname.getText()+":"+p.getSurname());
        tvAddress.setText(tvAddress.getText()+":"+p.getAddress());
        tvEmail.setText(tvEmail.getText()+":"+p.getEmail());
        tvPhone.setText(tvPhone.getText()+":"+p.getNumber());
        tvPosition.setText(tvPosition.getText()+":"+p.getPosition());
        Picasso.get()
                .load(p.getUrlPhoto())
                .into(imagePhoto);
    }

}