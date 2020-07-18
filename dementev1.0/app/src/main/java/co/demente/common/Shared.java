package co.demente.common;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import co.demente.events.EventBus;
import co.demente.engine.Engine;
import co.demente.negocio.services.Services;


public class Shared {

	public static Context context;
	public static FragmentActivity activity;
	public static Engine engine;
	public static EventBus eventBus;
	public static Services services;

}
