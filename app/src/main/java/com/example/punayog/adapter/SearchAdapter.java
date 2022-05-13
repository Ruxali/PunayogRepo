//package com.example.punayog.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.punayog.R;
//import com.example.punayog.SearchDeal;
//import com.example.punayog.model.Product;
//
//import java.util.ArrayList;
//
//public class SearchAdapter extends Filter {
//
//    private ProductAdapter adapter;
//    private ArrayList<Product> filterList;
//
//    public SearchAdapter(ProductAdapter adapter, ArrayList<Product> filterList) {
//        this.adapter = adapter;
//        this.filterList = filterList;
//    }
//
//    @Override
//    protected FilterResults performFiltering(CharSequence constraint) {
//        FilterResults results = new FilterResults();
//
//
//
//        //validate data for serch
//        if(constraint != null && constraint.length() > 0){
//            constraint = constraint.toString().toUpperCase();
//            ArrayList<Product> filterModels = new ArrayList<>();
//            for(int i=0; i<filterList.size();i++){
//                if(filterList.get(i).getProductName().contains(constraint)){
//                    filterModels.add(filterList.get(i));
//                }
//            }
//            results.count = filterModels.size();
//            results.values = filterModels;
//        }
//        else{
//            results.count = filterList.size();
//            results.values = filterList;
//        }
//        return results;
//    }
//
//    @Override
//    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//      adapter.productArrayList = (ArrayList<Product>) filterResults.values;
//      adapter.notifyDataSetChanged();
//
//    }
//}
