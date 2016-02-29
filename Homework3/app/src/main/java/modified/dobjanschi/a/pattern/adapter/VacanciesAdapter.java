package modified.dobjanschi.a.pattern.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import modified.dobjanschi.a.pattern.R;
import modified.dobjanschi.a.pattern.service.model.Vacancy;


/**
 * Created by yasina on 14.02.16.
 */
public class VacanciesAdapter extends RecyclerView.Adapter<VacanciesAdapter.VacancyRowHolder>{

    public static final String TAG = "VacanciesAdapter";
    private List<Vacancy.Item> mVacancies;
    //private ListComplaintsAdapter adapter;
    private RecyclerView mRecyclerView;


    public VacanciesAdapter(List<Vacancy.Item> mVacanices) {
        this.mVacancies = mVacanices;
    }

    @Override
    public VacancyRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vacancy_recycle_view, null);
        return new VacancyRowHolder(view);
    }

    @Override
    public void onBindViewHolder(VacancyRowHolder mVacancy, int i) {
        final Vacancy.Item v = mVacancies.get(i);
        mVacancy.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, v.id + " is clicked");
            }
        });
        mVacancy.tvName.setText(v.name);
        Log.d(TAG, v.name);
        mVacancy.tvEmployerName.setText(v.employer.name);
        Log.d(TAG, v.employer.name);
        mVacancy.tvResponsibility.setText(v.snippet.responsibility);
        Log.d(TAG, v.snippet.responsibility);
        mVacancy.tvRequirement.setText(v.snippet.requirement);
        Log.d(TAG, v.snippet.requirement);

    }

    @Override
    public int getItemCount() {
        return (null != mVacancies ? mVacancies.size() : 0);
    }


    public static class VacancyRowHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvEmployerName;
        private TextView tvResponsibility;
        private TextView tvRequirement;
        private View v;

        public VacancyRowHolder(View view) {
            super(view);
            this.v = view;
            tvName = (TextView) view.findViewById(R.id.tv_vacancy_name);
            tvEmployerName = (TextView) view.findViewById(R.id.tv_vacancy_employer_name);
            tvResponsibility = (TextView) view.findViewById(R.id.tv_vacancy_snippet_requirement);
            tvRequirement = (TextView) view.findViewById(R.id.tv_vacancy_snippet_responsibility);
        }
    }
}

