<script setup>
import { ref, watch, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useTripStore } from "@/stores/trip";
import AdditionalParam from "./AdditionalParam.vue";
const props = defineProps({ date: String, page: Number });

const tripStore = useTripStore();
const { planList, addPlan, delPlan, setAdditionalParam } = tripStore;

const attractionList = ref([]);

onMounted(() => {
  console.log("플랜리스트", planList);
  for (let i = 0; i < planList.length; i++) {
    if (planList[i].page == props.page) {
      attractionList.value.push(planList[i]);
    }
  }
});

watch(
  () => planList,
  () => {
    attractionList.value = [];
    for (let i = 0; i < planList.length; i++) {
      if (planList[i].page == props.page) {
        attractionList.value.push(planList[i]);
      }
    }
  },
  { deep: true }
);

const deletePlan = (id) => {
  delPlan(id);
};

const setParam = (val1, val2, val3, val4) => {
  setAdditionalParam(val1, val2, val3, val4);
};
</script>

<template>
  <div class="">
    <template v-for="attraction in attractionList" :key="attraction.contentId">
      <b-card class="mt-3" :header="attraction.title ? attraction.title : attraction.addr1">
        <button
          @click="deletePlan(attraction.contentId)"
          class="border border-1 rounded-4 p-1 bg-gray-300 hover:bg-gray-400 mb-2"
        >
          일정삭제
        </button>
        <AdditionalParam :contentId="attraction.contentId" @set-param="setParam" />

        <pre class="m-0"><img :src="attraction.firstImage" :alt="attraction.firstImage"></pre>
      </b-card>
    </template>
  </div>
</template>

<style scoped></style>
