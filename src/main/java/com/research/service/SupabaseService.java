package com.research.service;

import io.github.supabase.SupabaseClient;
import io.github.supabase.data.PostgrestResult;
import io.github.supabase.data.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupabaseService {

    @Autowired
    private SupabaseClient supabaseClient;

    public <T> List<T> getAll(String tableName, Class<T> clazz) {
        PostgrestResult<T> result = supabaseClient.from(tableName)
                .select()
                .execute();
        return result.getData();
    }

    public <T> T getById(String tableName, String id, Class<T> clazz) {
        PostgrestResult<T> result = supabaseClient.from(tableName)
                .select()
                .eq("id", id)
                .single()
                .execute();
        return result.getData();
    }

    public <T> T create(String tableName, T data) {
        PostgrestResult<T> result = supabaseClient.from(tableName)
                .insert(data)
                .execute();
        return result.getData().get(0);
    }

    public <T> T update(String tableName, String id, T data) {
        PostgrestResult<T> result = supabaseClient.from(tableName)
                .update(data)
                .eq("id", id)
                .execute();
        return result.getData().get(0);
    }

    public void delete(String tableName, String id) {
        supabaseClient.from(tableName)
                .delete()
                .eq("id", id)
                .execute();
    }

    public <T> List<T> query(String tableName, QueryBuilder query, Class<T> clazz) {
        PostgrestResult<T> result = supabaseClient.from(tableName)
                .select()
                .query(query)
                .execute();
        return result.getData();
    }
}