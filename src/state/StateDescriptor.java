package state;

public enum StateDescriptor {
    quiescent {
        @Override
        public IState getInstance() {
            return new QuiescentState();
        }
    },

    excited {
        @Override
        public IState getInstance() {
            return new ExcitedState();
        }
    },

    refractory {
        @Override
        public IState getInstance() {
            return new RefractoryState();
        }
    };

    public abstract IState getInstance();
}
